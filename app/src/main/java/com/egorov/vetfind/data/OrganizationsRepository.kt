package com.egorov.vetfind.data

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class OrganizationsRepository @Inject constructor(
    organizationRemoteDataSource: OrganizationsRemoteDataSource
) {
    val remote = organizationRemoteDataSource
}