package com.vasyl.code.vasylchallenge.data.converters

import com.vasyl.code.vasylchallenge.data.converters.procedure.toDomain
import com.vasyl.code.vasylchallenge.data.converters.procedure.toEntity
import com.vasyl.code.vasylchallenge.data.local.model.PhaseEntity
import com.vasyl.code.vasylchallenge.data.local.model.ProcedureDetailEntity
import com.vasyl.code.vasylchallenge.data.local.model.ProcedureEntity
import com.vasyl.code.vasylchallenge.data.remote.model.icon.IconDto
import com.vasyl.code.vasylchallenge.data.remote.model.procedure.PhaseDto
import com.vasyl.code.vasylchallenge.data.remote.model.procedure.ProcedureDetailsDto
import com.vasyl.code.vasylchallenge.data.remote.model.procedure.ProcedureDto
import org.junit.Test
import kotlin.test.assertEquals

class ProcedureConvertersTest {

    @Test
    fun `ProcedureEntity toDomain should correctly map to Procedure`() {
        val entity = ProcedureEntity(
            uuid = "123",
            iconUrl = "icon.png",
            name = "Test Procedure",
            phases = emptyList(),
            isFavorite = true
        )

        val result = entity.toDomain()

        assertEquals(entity.uuid, result.uuid)
        assertEquals(entity.iconUrl, result.iconUrl)
        assertEquals(entity.name, result.name)
        assertEquals(entity.phases, result.phases)
        assertEquals(entity.isFavorite, result.isFavorite)
    }

    @Test
    fun `ProcedureDto toEntity should correctly map to ProcedureEntity`() {
        val dto = ProcedureDto(
            uuid = "123",
            icon = IconDto(uuid = "test", url = "test", version = 100),
            name = "Test Procedure",
            phases = emptyList()
        )

        val result = dto.toEntity(isFavourite = true)

        assertEquals(dto.uuid, result.uuid)
        assertEquals(dto.icon?.url, result.iconUrl)
        assertEquals(dto.name, result.name)
        assertEquals(dto.phases, result.phases)
        assertEquals(true, result.isFavorite)
    }

    @Test
    fun `ProcedureDto toDomain should correctly map to Procedure`() {
        val dto = ProcedureDto(
            uuid = "123",
            icon = IconDto(uuid = "test", url = "test", version = 100),
            name = "Test Procedure",
            phases = emptyList()
        )

        val result = dto.toDomain(isFavourite = false)

        assertEquals(dto.uuid, result.uuid)
        assertEquals(dto.icon?.url, result.iconUrl)
        assertEquals(dto.name, result.name)
        assertEquals(dto.phases, result.phases)
        assertEquals(false, result.isFavorite)
    }

    @Test
    fun `ProcedureDetailEntity toDomain should correctly map to ProcedureDetails`() {
        val entity = ProcedureDetailEntity(
            uuid = "456",
            name = "Detail Name",
            phases = emptyList(),
            cardUrl = "card.png",
            datePublished = "2024-02-27",
            duration = 120
        )

        val result = entity.toDomain(isFavourite = true)

        assertEquals(entity.uuid, result.uuid)
        assertEquals(entity.name, result.name)
        assertEquals(entity.phases?.map { it.toDomain() }, result.phases)
        assertEquals(entity.cardUrl, result.cardUrl)
        assertEquals(entity.datePublished, result.datePublished)
        assertEquals(entity.duration, result.duration)
        assertEquals(true, result.isFavourite)
    }

    @Test
    fun `PhaseEntity toDomain should correctly map to Phase`() {
        val entity = PhaseEntity(uuid = "789", name = "Phase Name", iconUrl = "phase.png")

        val result = entity.toDomain()

        assertEquals(entity.uuid, result.uuid)
        assertEquals(entity.name, result.name)
        assertEquals(entity.iconUrl, result.iconUrl)
    }

    @Test
    fun `ProcedureDetailsDto toEntity should correctly map to ProcedureDetailEntity`() {
        val dto = ProcedureDetailsDto(
            uuid = "456",
            name = "Detail Name",
            phases = emptyList(),
            card = IconDto(uuid = "test", url = "test", version = 100),
            datePublished = "2024-02-27",
            duration = 120
        )

        val result = dto.toEntity()

        assertEquals(dto.uuid, result.uuid)
        assertEquals(dto.name, result.name)
        assertEquals(dto.phases?.map { it.toEntity() }, result.phases)
        assertEquals(dto.card?.url, result.cardUrl)
        assertEquals(dto.datePublished, result.datePublished)
        assertEquals(dto.duration, result.duration)
    }

    @Test
    fun `ProcedureDetailsDto toDomain should correctly map to ProcedureDetails`() {
        val dto = ProcedureDetailsDto(
            uuid = "456",
            name = "Detail Name",
            phases = emptyList(),
            card = IconDto(uuid = "test", url = "test", version = 100),
            datePublished = "2024-02-27",
            duration = 120
        )

        val result = dto.toDomain(isFavourite = false)

        assertEquals(dto.uuid, result.uuid)
        assertEquals(dto.name, result.name)
        assertEquals(dto.phases?.map { it.toDomain() }, result.phases)
        assertEquals(dto.card?.url, result.cardUrl)
        assertEquals(dto.datePublished, result.datePublished)
        assertEquals(dto.duration, result.duration)
        assertEquals(false, result.isFavourite)
    }

    @Test
    fun `PhaseDto toDomain should correctly map to Phase`() {
        val dto = PhaseDto(uuid = "789", name = "Phase Name",  icon = IconDto(uuid = "test", url = "test", version = 100),)

        val result = dto.toDomain()

        assertEquals(dto.uuid, result.uuid)
        assertEquals(dto.name, result.name)
        assertEquals(dto.icon?.url, result.iconUrl)
    }

    @Test
    fun `PhaseDto toEntity should correctly map to PhaseEntity`() {
        val dto = PhaseDto(uuid = "789", name = "Phase Name",  icon = IconDto(uuid = "test", url = "test", version = 100))

        val result = dto.toEntity()

        assertEquals(dto.uuid, result.uuid)
        assertEquals(dto.name, result.name)
        assertEquals(dto.icon?.url, result.iconUrl)
    }
}
