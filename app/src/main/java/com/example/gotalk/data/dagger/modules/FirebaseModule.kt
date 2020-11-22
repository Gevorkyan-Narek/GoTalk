package com.example.gotalk.data.dagger.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun getFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun getFirebaseDatabase() = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun getFirebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun getStorageReference(firebaseStorage: FirebaseStorage) = firebaseStorage.reference.child("images")

    @Provides
    @Singleton
    fun getDatabaseReference(database: FirebaseDatabase) = database.reference

    @Provides
    @Singleton
    fun getCurrentUserDatabaseReference(firebaseAuth: FirebaseAuth) = firebaseAuth.currentUser!!
}