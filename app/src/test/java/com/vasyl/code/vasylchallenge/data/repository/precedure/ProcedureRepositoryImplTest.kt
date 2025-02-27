package com.vasyl.code.vasylchallenge.data.repository.precedure

import com.vasyl.code.vasylchallenge.data.converters.procedure.toDomain
import com.vasyl.code.vasylchallenge.data.converters.procedure.toEntity
import com.vasyl.code.vasylchallenge.data.local.dao.ProcedureDao
import com.vasyl.code.vasylchallenge.data.local.dao.ProcedureDetailsDao
import com.vasyl.code.vasylchallenge.data.remote.model.icon.IconDto
import com.vasyl.code.vasylchallenge.data.remote.model.procedure.PhaseDto
import com.vasyl.code.vasylchallenge.data.remote.model.procedure.ProcedureDetailsDto
import com.vasyl.code.vasylchallenge.data.remote.model.procedure.ProcedureDto
import com.vasyl.code.vasylchallenge.data.remote.service.ProcedureService
import com.vasyl.code.vasylchallenge.data.repository.procedure.ProcedureRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertFailsWith


@RunWith(MockitoJUnitRunner::class)
class ProcedureRepositoryImplTest {

    private lateinit var procedureRepository: ProcedureRepositoryImpl
    private val procedureService: ProcedureService = mock(ProcedureService::class.java)
    private val procedureDao: ProcedureDao = mock(ProcedureDao::class.java)
    private val procedureDetailsDao: ProcedureDetailsDao = mock(ProcedureDetailsDao::class.java)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        procedureRepository = ProcedureRepositoryImpl(procedureService, procedureDao, procedureDetailsDao)
    }

    @Test
    fun `getProcedures should return procedures from remote source`() = runTest(testDispatcher) {
        `when`(procedureService.getProcedures()).thenReturn(mockProceduresDto)
        `when`(procedureDao.getFavoriteUuids()).thenReturn(emptyList())

        val result = procedureRepository.getProcedures()

        assertEquals(mockProcedures, result)
    }

    @Test
    fun `getProcedures should return local data when remote fails`() = runTest(testDispatcher) {
        `when`(procedureService.getProcedures()).thenThrow(RuntimeException("Network error"))
        `when`(procedureDao.getProcedures()).thenReturn(mockProceduresEntities)

        val result = procedureRepository.getProcedures()

        assertEquals(mockProcedures, result)
    }

    @Test
    fun `getProcedures should throw exception when both remote and local fail`() = runTest(testDispatcher) {
        `when`(procedureService.getProcedures()).thenThrow(RuntimeException("Network error"))
        `when`(procedureDao.getProcedures()).thenReturn(emptyList())

        assertFailsWith<RuntimeException> { procedureRepository.getProcedures() }
    }

    @Test
    fun `getProcedureDetails should return details from remote source`() = runTest(testDispatcher) {
        val testUUID = "test_uuid"
        `when`(procedureService.getProcedureDetails(testUUID)).thenReturn(mockProcedureDetails)
        `when`(procedureDao.getFavoriteUuids()).thenReturn(emptyList())

        val result = procedureRepository.getProcedureDetails(testUUID)

        assertEquals(mockProcedureDetails.toDomain(false), result)
    }

    @Test
    fun `getProcedureDetails should throw exception when both remote and local fail`() = runTest(testDispatcher) {
        val testUuid = "test_uuid"
        `when`(procedureService.getProcedureDetails(testUuid)).thenThrow(RuntimeException("Network error"))
        `when`(procedureDetailsDao.getProcedureDetailsByUuid(testUuid)).thenReturn(null)

        assertFailsWith<RuntimeException> { procedureRepository.getProcedureDetails(testUuid) }
    }

    @Test
    fun `toggleProcedureFavoriteStatus should update favorite status in database`() = runTest(testDispatcher) {
        val procedureUuid = "123"
        val isFavorite = true

        procedureRepository.toggleProcedureFavoriteStatus(procedureUuid, isFavorite)

        verify(procedureDao).updateProcedureFavoriteStatus(procedureUuid, isFavorite)
    }

    @Test
    fun `getFavouriteProcedures should return procedures marked as favorite`() = runTest(testDispatcher) {
        `when`(procedureDao.getFavoriteProcedures()).thenReturn(mockFavouriteProceduresEntities)

        val result = procedureRepository.getFavouriteProcedures()

        assertEquals(mockFavouriteProceduresEntities.map { it.toDomain() }, result)
    }

    private companion object {
        val mockIconDto = IconDto(
            uuid = "test_uuid",
            url = "test_url",
            version = 100
        )

        val mockProcedureDto = ProcedureDto(
            uuid = "uuid",
            name = "name",
            phases = listOf("phase1", "phase2"),
            icon = mockIconDto,
        )

        val mockProceduresDto = listOf(
            mockProcedureDto, mockProcedureDto.copy(uuid = "test_2")
        )

        val mockProcedures = mockProceduresDto.map { it.toDomain(false) }

        val mockProceduresEntities = mockProceduresDto.map { it.toEntity(false) }

        val mockFavouriteProceduresEntities = mockProceduresDto.map { it.toEntity(false) }


        val mockProcedurePhase = PhaseDto(
            uuid = "test_uuid",
            name = "test_name",
            icon = mockIconDto
        )
        val mockProcedureDetails = ProcedureDetailsDto(
            uuid = "test_uuid",
            name = "test_name",
            phases = listOf(mockProcedurePhase),
            card = mockIconDto,
            duration = 74,
            datePublished = "2025-02-20T10:05:15.875180"
        )
    }
}
