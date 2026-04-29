package saru.archievment.backend.core.service

import saru.archievment.backend.core.data.AbstractEntity
import saru.archievment.backend.core.repository.AbstractEntityRepository

abstract class AbstractEntityService<S : AbstractEntity, T : AbstractEntityRepository<S>>(
    val repository: T,
) {

    fun findAll(): List<S> {
        return repository.findAll()
    }

    fun save(entity: S): S {
        return repository.save(entity)
    }

}