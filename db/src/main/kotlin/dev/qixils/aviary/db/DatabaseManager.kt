package dev.qixils.aviary.db

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import org.bson.conversions.Bson
import reactor.core.publisher.Flux
import java.io.Closeable

// TODO: allow servers to add custom codecs (serializers) (i.e. for Minestom objects)
// TODO: idk if i love that this is using mongo

/**
 * Manages the connection to a MongoDB database.
 */
class DatabaseManager(dbPrefix: String, dbSuffix: String) : Closeable {
	private val pojoRegistry = CodecRegistries.fromRegistries(
		MongoClientSettings.getDefaultCodecRegistry(),
		CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
	)
	private val mongoClientSettings = MongoClientSettings.builder()
		.applyConnectionString(ConnectionString("mongodb://localhost:27017"))
		.codecRegistry(pojoRegistry)
		.build()
	private val mongoClient = MongoClients.create(mongoClientSettings)
	private val database: MongoDatabase

	constructor(dbPrefix: String, environment: Environment) : this(
		dbPrefix,
		environment.name.lowercase()
	)

	init {
		database = mongoClient.getDatabase("$dbPrefix-$dbSuffix")
	}

	// collection
	fun <T> collection(tClass: Class<T>): MongoCollection<T> {
		return collection(collectionNameOf(tClass), tClass)
	}

	fun <T> collection(collectionName: String, tClass: Class<T>): MongoCollection<T> {
		return database.getCollection(collectionName, tClass)
	}

	// getAll
	fun <T> getAll(tClass: Class<T>): Flux<T> {
		return Flux.from(collection(tClass).find())
	}

	// getAllBy
	fun <T> getAllBy(filters: Bson, tClass: Class<T>): Flux<T> {
		return Flux.from(collection(tClass).find(filters))
	}

	fun <T> getAllByEquals(keyValueMap: Map<String?, *>, tClass: Class<T>): Flux<T> {
		var filter = Filters.empty()
		for ((key, value) in keyValueMap) filter = Filters.and(
			filter, Filters.eq(
				key, value
			)
		)
		return getAllBy(filter, tClass)
	}

	// TODO: getByUUID
	// TODO: updateBy
	// TODO: updateByUUID
	// TODO: editBy (getBy + updateBy)
	// TODO: editByUUID (getByUUID + updateByUUID)

	// misc
	override fun close() {
		mongoClient.close()
	}

	companion object {
		// collectionNameOf
		private fun collectionNameOf(obj: Any): String {
			return collectionNameOf(obj.javaClass)
		}

		private fun collectionNameOf(jClass: Class<*>): String {
			val collectionName = if (jClass.isAnnotationPresent(CollectionName::class.java))
				jClass.getAnnotation(CollectionName::class.java).name
			else
				jClass.simpleName
			return collectionName.substring(0, 127)
		}
	}
}
