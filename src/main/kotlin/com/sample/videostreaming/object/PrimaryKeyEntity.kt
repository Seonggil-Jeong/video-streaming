package com.sample.videostreaming.`object`

import org.hibernate.annotations.GenericGenerator
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.jvm.Transient

@MappedSuperclass
class PrimaryKeyEntity : Persistable<UUID> {
    @Id
    @Column(columnDefinition = "uuid")
    private val id: UUID = UUID.randomUUID()

    @Transient
    private var _isNew: Boolean = true

    override fun getId(): UUID? = id

    override fun isNew(): Boolean = _isNew

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other !is HibernateProxy && this::class != other::class) {
            return false
        }

        return id == getIdentifier(other)
    }

    override fun hashCode() = Objects.hashCode(id)

    private fun getIdentifier(obj: Any): Serializable {
        return if (obj is HibernateProxy) {
            obj.hibernateLazyInitializer.identifier
        } else {
            (obj as PrimaryKeyEntity).id
        }
    }

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }
}