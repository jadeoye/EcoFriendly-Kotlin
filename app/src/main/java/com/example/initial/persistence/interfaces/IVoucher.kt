package com.example.initial.persistence.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.initial.persistence.entities.Voucher

@Dao
interface IVoucher {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(voucher: Voucher)

    @Query("SELECT * FROM vouchers WHERE ownerId = :ownerId AND isDeleted = 0")
    suspend fun list(ownerId: Int): List<Voucher>

    @Update
    suspend fun update(voucher: Voucher)

    @Query("SELECT * FROM vouchers WHERE ownerId = :ownerId AND isDeleted = 0 AND isConsumed = 0 ORDER BY createdOn LIMIT 1")
    suspend fun getUnusedVoucher(ownerId: Int): Voucher?

    @Query("DELETE FROM vouchers WHERE id = :donationId")
    suspend fun delete(donationId: Int)

    @Query("SELECT * FROM vouchers WHERE id = :voucherId AND isDeleted = 0")
    suspend fun get(voucherId: Int): Voucher?
}