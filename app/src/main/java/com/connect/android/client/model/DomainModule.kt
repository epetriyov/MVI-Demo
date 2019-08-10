package com.connect.android.client.model

import com.connect.android.client.model.auth.*
import com.connect.android.client.model.chats.ChatsApi
import com.connect.android.client.model.chats.ChatsRepoImpl
import com.connect.android.client.model.chats.ChatsRepository
import com.connect.android.client.model.common.*
import com.connect.android.client.model.contacts.ContactsApi
import com.connect.android.client.model.contacts.ContactsRepoImpl
import com.connect.android.client.model.contacts.ContactsRepository
import com.connect.android.client.model.educations.EducationsApi
import com.connect.android.client.model.educations.EducationsRepoImpl
import com.connect.android.client.model.educations.EducationsRepository
import com.connect.android.client.model.events.*
import com.connect.android.client.model.location.*
import com.connect.android.client.model.messages.*
import com.connect.android.client.model.profile.ProfileApi
import com.connect.android.client.model.profile.ProfileRepoImpl
import com.connect.android.client.model.profile.ProfileRepository
import com.connect.android.client.model.recommendations.RecommendationsApi
import com.connect.android.client.model.recommendations.RecommendationsRepoImpl
import com.connect.android.client.model.recommendations.RecommendationsRepository
import com.connect.android.client.model.works.WorksApi
import com.connect.android.client.model.works.WorksRepoImpl
import com.connect.android.client.model.works.WorksRepository
import com.connect.android.client.tools.room.ConnectDatabase
import com.tinder.scarlet.Scarlet
import org.koin.dsl.module
import retrofit2.Retrofit


val authModule = module {
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(AuthApi::class.java)
    }
    factory<AuthRepository> { AuthRepoImpl(get(), get(), get(), get(), get()) }
}

val chatsModule = module {
    factory {
        val db: ConnectDatabase = get()
        db.chatDao()
    }
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(ChatsApi::class.java)
    }
    factory<ChatsRepository> {
        ChatsRepoImpl(get(), get())
    }
}

val commonModule = module {
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(CommonApi::class.java)
    }
    factory<CommonStore> {
        SharedCommonStore(get())
    }
    factory<CommonRepository> {
        CommonRepoImpl(get(), get())
    }
}

val contactsModule = module {
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(ContactsApi::class.java)
    }
    factory {
        val db: ConnectDatabase = get()
        db.contactsDao()
    }
    factory<ContactsRepository> {
        ContactsRepoImpl(get(), get())
    }
}

val educationsModule = module {
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(EducationsApi::class.java)
    }
    factory<EducationsRepository> {
        EducationsRepoImpl(get())
    }
}

val eventsModule = module {
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(EventsApi::class.java)
    }
    factory {
        val db: ConnectDatabase = get()
        db.eventDao()
    }
    factory<EventMembersStorage> { RamEventsMembersStorage() }
    factory<EventsRepository> {
        EventsRepoImpl(get(), get(), get())
    }
}

val locationModule = module {
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(LocationApi::class.java)
    }
    factory<LocationProvider> {
        LocationProviderImpl(get())
    }
    factory<LocationRepository> {
        LocationRepoImpl(get(), get())
    }
}

val messagesModule = module {
    factory {
        val scarlet: Scarlet = get()
        scarlet.create(ChatApi::class.java)
    }
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(MessagesApi::class.java)
    }
    factory {
        val db: ConnectDatabase = get()
        db.messageDao()
    }
    factory<MessagesRepository> {
        MessagesRepoImpl(get(), get())
    }
    factory<ChatEngine> {
        ChatEngineImpl(get(), get())
    }
}

val profileModule = module {
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(ProfileApi::class.java)
    }
    factory<ProfileStore> {
        SharedProfileStore(get())
    }
    factory<ProfileRepository> {
        ProfileRepoImpl(get(), get())
    }
}

val recommendationsModule = module {
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(RecommendationsApi::class.java)
    }
    factory<RecommendationsRepository> { RecommendationsRepoImpl(get()) }
}

val worksModule = module {
    factory {
        val retrofit: Retrofit = get()
        retrofit.create(WorksApi::class.java)
    }
    factory<WorksRepository> { WorksRepoImpl(get()) }
}