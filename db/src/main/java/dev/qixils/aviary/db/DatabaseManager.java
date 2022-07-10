package dev.qixils.aviary.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

import java.io.Closeable;
import java.util.Locale;
import java.util.Map;

/**
 * Manages the connection to a MongoDB database.
 */
public class DatabaseManager implements Closeable {
	private final @NotNull CodecRegistry pojoRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
			CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
	private final @NotNull MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
			.applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
			.codecRegistry(pojoRegistry)
			.build();
	private final @NotNull MongoClient mongoClient = MongoClients.create(mongoClientSettings);
	protected final @NotNull MongoDatabase database;

	public DatabaseManager(@NotNull String dbPrefix, @NotNull Environment environment) {
		this(dbPrefix, environment.name().toLowerCase(Locale.ROOT));
	}

	public DatabaseManager(@NotNull String dbPrefix, @NotNull String dbSuffix) {
		this.database = mongoClient.getDatabase(dbPrefix + "-" + dbSuffix);
	}

	// collectionNameOf

	private static String collectionNameOf(Object object) {
		return collectionNameOf(object.getClass());
	}

	private static String collectionNameOf(Class<?> clazz) {
		String collectionName;
		if (clazz.isAnnotationPresent(CollectionName.class))
			collectionName = clazz.getAnnotation(CollectionName.class).name();
		else
			collectionName = clazz.getSimpleName();
		return collectionName.substring(0, 127);
	}

	// collection

	public <T> @NotNull MongoCollection<T> collection(@NotNull Class<T> tClass) {
		return collection(collectionNameOf(tClass), tClass);
	}

	public <T> @NotNull MongoCollection<T> collection(@NotNull String collectionName, @NotNull Class<T> tClass) {
		return database.getCollection(collectionName, tClass);
	}

	// getAll

	public <T> @NotNull Flux<T> getAll(@NotNull Class<T> tClass) {
		return Flux.from(collection(tClass).find());
	}

	// getAllBy

	public <T> @NotNull Flux<T> getAllBy(@NotNull Bson filters, @NotNull Class<T> tClass) {
		return Flux.from(collection(tClass).find(filters));
	}

	public <T> @NotNull Flux<T> getAllByEquals(@NotNull Map<String, ?> keyValueMap, @NotNull Class<T> tClass) {
		Bson filter = Filters.empty();
		for (Map.Entry<String, ?> entry : keyValueMap.entrySet())
			filter = Filters.and(filter, Filters.eq(entry.getKey(), entry.getValue()));
		return getAllBy(filter, tClass);
	}

	// TODO: getByUUID

	// misc

	public void close() {
		mongoClient.close();
	}
}
