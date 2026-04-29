package saru.archievment.backend.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import saru.archievment.backend.core.data.AbstractEntity

@NoRepositoryBean
interface AbstractEntityRepository<S : AbstractEntity> : JpaRepository<S, Long>