package com.example.kelompoksatuuser.model

class Member {
    var idMember: String? = null
    var namaDepanMember: String? = null
    var namaBelakangMember: String? = null
    var nomorMember: String? = null
    var emailMember: String? = null
    var passwordMember: String? = null
    var alamatMember: String? = null
    var fotoMember: String? = null

    constructor() {}

    constructor(
        idMember: String?,
        namaDepanMember: String?,
        namaBelakangMember: String?,
        nomorMember: String?,
        emailMember: String?,
        passwordMember: String?,
        alamatMember: String?,
        fotoMember: String?
    ) {
        this.idMember = idMember
        this.namaDepanMember = namaDepanMember
        this.namaBelakangMember = namaBelakangMember
        this.nomorMember = nomorMember
        this.emailMember = emailMember
        this.passwordMember = passwordMember
        this.alamatMember = alamatMember
        this.fotoMember = fotoMember
    }
}