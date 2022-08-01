package dev.qixils.aviary.db

/**
 * Specifies the collection name corresponding to a POJO class.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CollectionName(val name: String)
