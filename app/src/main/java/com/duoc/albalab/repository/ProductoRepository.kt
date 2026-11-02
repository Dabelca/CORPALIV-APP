package com.duoc.albalab.repository
import com.duoc.albalab.model.Producto

class ProductoRepository {

    private val productos = listOf (
        Producto(1, "Bolsa Alba Lab", "Bolsa ecológica de algodón medidas 40x60cm. Lavable y reutilizable. Personalizada con frase alba lab", 4000, "Otros"),
        Producto(2, "Difusor Colgante", "Aromatizador de 8 ml con piedras naturales para usar en espacios reducidos como auto, closet, velador. Envase de vidrio transparente.", 7500, "Bienestar"),
        Producto(3, "Difusor de aroma 100ml", "Aromatizador de ambiente en spray. Envase pet. 100 ml. Aromas disponibles: Vainilla, Alba, Coco lavanda, Verbena Limón.", 7500, "Bienestar"),
        Producto(4, "Difusor de aroma 150ml", "Aromatizador de ambiente en spray con piedras naturales. Envase pet. 150 ml. Aromas disponibles: Vainilla, Alba, Coco lavanda, Verbena Limón.", 10000, "Bienestar"),
        Producto(5, "Difusor de aroma 250 ml", "Aromatizador de ambiente en spray. Envase pet. 250 ml. Formato ideal para el hogar. Aromas disponibles: Vainilla, Alba, Coco lavanda, Verbena Limón", 12000, "Bienestar"),
        Producto(6, "Difusor de varilla", "Aromatizador de ambientes de 150 ml con piedras naturales y varillas de bamboo. Envase de vidrio transparente.", 17000, "Bienestar"),
        Producto(7, "Farol de luz", "Con una altura de 12 cm y un diámetro de 7 cm, su tamaño compacto lo hace perfecto para decorar mesas, estanterías o rincones especiales. Este farol es un tributo a la calma y la naturaleza, ideal para quienes buscan momentos de paz en su día a día.", 11900,"Otros"),
        Producto(8, "Guatero de semillas", "Nuestro guatero de semillas es una mezcla artesanal de ingredientes naturales que te brindan confort y bienestar. Contiene una combinación de arroz y mijo, que retienen el calor por más tiempo, acompañado de hierbas cuidadosamente seleccionadas como eucalipto, tomillo, romero, manzanilla y cáscaras de naranja. Estas hierbas no solo potencian la relajación, sino que, al calentarse, liberan un suave aroma que calma los sentidos y promueve una sensación de bienestar general.", 11900, "Bienestar")

    )

    fun obtenerProductos(): List<Producto> = productos

    fun obtenerTipos(): List<String> =productos.map { it.tipo }.distinct()
}