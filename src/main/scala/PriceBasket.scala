package org.basket

import Calculations._

object PriceBasket extends App {
  if (args.length < 1) {
    sys.error("Error: No basket items provided")
  }
  val basket = args.toList

  private val unknownGoods = basket.filterNot(inventory.contains)
  if (unknownGoods.nonEmpty) {
    sys.error(s"Basket Contains unknown Items => ${unknownGoods.mkString(", ")}")
  }

  private val basketValue = priceBasket(basket)

  private val subTotal = basketValue._1
  val discounts = basketValue._2
  private val total = basketValue._3

  private var hasDiscounts = false

  println(s"Subtotal: £$subTotal")
  if (discounts("Apples") > 0) {
    println(s"Apples 10% off: £${discounts("Apples")}")
    hasDiscounts = true
  }

  if (discounts("Bread") > 0) {
    println(s"Bread 50% off: £${discounts("Bread")}")
    hasDiscounts = true
  }

  if (!hasDiscounts) {
    println("(No offers available)")
  }
  println(s"Total price: £$total")
}
