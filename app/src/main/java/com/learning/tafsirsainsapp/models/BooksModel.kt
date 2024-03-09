package com.learning.tafsirsainsapp.models

import java.io.Serializable

data class BooksModel(
    val image: String = "",
    val imagearab: String = "",
    val imageinggris: String = "",

    val title: String = "",
    val titlearab: String = "",
    val titleinggris: String = "",

    val description: String = "",
    val descriptionarab: String = "",
    val descriptioninggris: String = "",

    val bkdescript: String = "",
    val bkdescriptarab: String = "",
    val bkdescriptinggris: String = "",

    val author: String = "",
    val authorarab: String = "",
    val authoringgris: String = "",

    val bookPDF: String = "",
    val bookPDFarab: String = "",
    val bookPDFinggris: String = "",

):Serializable

