package com.jhoglas.mysalon.data.remote

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.kotlintest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AuthDataSourceImplTest {
    private val mockDatabase: FirebaseDatabase = mockk(relaxed = true)
    private val mockDatabaseReference: DatabaseReference = mockk(relaxed = true)
    private val tested = AuthDataSourceImpl(mockDatabase)
    private val user = UserRemoteEntity(
        "Test Name",
        "test@mail.com",
        "123456789",
        "test.png",
        "2022-02-13",
        "2022-02-13"
    )

    private val mockDataSnapshot: DataSnapshot = mockk {
        every { child("name").value } returns user.name
        every { child("email").value } returns user.email
        every { child("phoneNumber").value } returns user.phoneNumber
        every { child("dateCreate").value } returns user.dateCreate
        every { child("dateUpdate").value } returns user.dateUpdate
        every { child("image").value } returns user.image
    }

    private val mockDataSnapshotError: DataSnapshot = mockk {
        every { child("name").value } returns null
        every { child("email").value } returns null
        every { child("phoneNumber").value } returns null
        every { child("image").value } returns null
        every { child("dateCreate").value } returns null
        every { child("dateUpdate").value } returns null
    }

    @Test
    fun `setUser successfully`() = runTest {
        every { mockDatabase.getReference("accounts") } returns mockDatabaseReference
        every { mockDatabaseReference.child(any()) } returns mockDatabaseReference

        val taskSlot = slot<OnCompleteListener<Void>>()
        val mockTask: Task<Void> = mockk {
            every { isSuccessful } returns true
        }

        coEvery {
            mockDatabaseReference.setValue(any<UserRemoteEntity>())
                .addOnCompleteListener(capture(taskSlot))
        } answers {
            taskSlot.captured.onComplete(mockTask)
            mockTask
        }

        val flow = tested.setUser("testId", user)

        flow.collect {
            it shouldBe true
        }
    }

    @Test
    fun `setUser unsuccessfully`() = runTest {
        every { mockDatabase.getReference("accounts") } returns mockDatabaseReference
        every { mockDatabaseReference.child(any()) } returns mockDatabaseReference

        val taskSlot = slot<OnCompleteListener<Void>>()
        val mockTask: Task<Void> = mockk {
            every { isSuccessful } returns false
        }

        coEvery {
            mockDatabaseReference.setValue(any<UserRemoteEntity>())
                .addOnCompleteListener(capture(taskSlot))
        } answers {
            taskSlot.captured.onComplete(mockTask)
            mockTask
        }

        val flow = tested.setUser("testId", user)

        flow.collect {
            it shouldBe false
        }
    }

    @Test
    fun `getUser successfully`() = runTest {
        every { mockDatabase.getReference("accounts") } returns mockDatabaseReference
        every { mockDatabaseReference.child(any()) } returns mockDatabaseReference

        val taskSlot = slot<OnCompleteListener<DataSnapshot>>()

        val mockTask: Task<DataSnapshot> = mockk {
            every { isSuccessful } returns true
            every { result } returns mockDataSnapshot
        }

        every {
            mockDatabaseReference.get().addOnCompleteListener(capture(taskSlot))
        } answers {
            taskSlot.captured.onComplete(mockTask)
            mockTask
        }

        val flow = tested.getUser("testId")

        flow.collect {
            it shouldBe user
        }
    }

    @Test
    fun `getUser unsuccessfully`() = runTest {
        every { mockDatabase.getReference("accounts") } returns mockDatabaseReference
        every { mockDatabaseReference.child(any()) } returns mockDatabaseReference
        val taskSlot = slot<OnCompleteListener<DataSnapshot>>()

        val mockTask: Task<DataSnapshot> = mockk {
            every { isSuccessful } returns false
            every { result } returns mockDataSnapshotError
        }

        every {
            mockDatabaseReference.get().addOnCompleteListener(capture(taskSlot))
        } answers {
            taskSlot.captured.onComplete(mockTask)
            mockTask
        }

        val flow = tested.getUser("testId")

        flow.collect {
            it.name shouldBe "null"
            it.email shouldBe "null"
            it.phoneNumber shouldBe "null"
            it.image shouldBe "null"
            it.dateCreate shouldBe "null"
            it.dateUpdate shouldBe "null"
        }
    }
}
