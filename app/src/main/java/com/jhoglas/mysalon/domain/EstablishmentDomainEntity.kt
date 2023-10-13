package com.jhoglas.mysalon.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EstablishmentDomainEntity(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val img: String = "",
    val services: List<String> = emptyList(),
    val rating: Float = 0f
) : Parcelable

fun getEstablishments(): List<EstablishmentDomainEntity> = listOf(
    EstablishmentDomainEntity(
        id = "1",
        name = "Barber Shop",
        img = "https://img.myloview.com.br/posters/barber-shop-logo-with-barber-pole-in-vintage-style-vector-template-700-162589723.jpg",
        address = "Rua Jose de Abreu, 123, Centro, São Paulo - SP",
        services = listOf("Corte de Cabelo", "Barba", "Pintura de Cabelo"),
        rating = 4.5f
    ),
    EstablishmentDomainEntity(
        id = "2",
        name = "Ladies Salon",
        img = "https://static.vecteezy.com/system/resources/previews/007/243/072/non_2x/beauty-salon-hair-logo-free-vector.jpg",
        address = "Rua Joao da Costa Neto, 123, Centro, São Paulo - SP",
        services = listOf("Corte de Cabelo", "Pintura de Cabelo"),
        rating = 4f
    ),
    EstablishmentDomainEntity(
        id = "3",
        name = "Car Wash",
        img = "https://static.vecteezy.com/ti/vetor-gratis/p1/7688848-logotipo-da-lavagem-de-carros-gratis-vetor.jpg",
        address = "Rua Jose Paulo da Fonseca, 123, Centro, São Paulo - SP",
        services = listOf("Lavagem", "Polimento", "Aspiração"),
        rating = 3.5f
    ),
    EstablishmentDomainEntity(
        id = "4",
        name = "Pet Shop Debora",
        img = "https://cdn.iset.io/assets/55268/produtos/19996/adesivo-de-parede-cachorrinho-pet-shop-1.jpg",
        address = "Rua Maria Luiza, 123, Centro, São Paulo - SP",
        services = listOf("Banho", "Tosa", "Corte de Unha"),
        rating = 5f
    ),
    EstablishmentDomainEntity(
        id = "5",
        name = "Pet Shop Jhon",
        img = "https://img.elo7.com.br/product/zoom/251D14F/adesivo-de-parede-pet-shop-2-2-reciclado.jpg",
        address = "Rua Do Botequin, 123, Centro, São Paulo - SP",
        services = listOf("Banho", "Tosa", "Corte de Unha"),
        rating = 4.5f
    ),
    EstablishmentDomainEntity(
        id = "6",
        name = "Nail Salon",
        img = "https://www.byronbayescapes.com/wp-content/uploads/2022/09/Nail-Salon-in-Byron-Bay-1024x683.jpg",
        address = "Rua Das Descorbertas, 123, Centro, São Paulo - SP",
        services = listOf("Manicure", "Pedicure", "Unhas de Gel"),
        rating = 4f
    )
)
