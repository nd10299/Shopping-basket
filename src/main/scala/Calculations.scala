package org.basket

import scala.collection.mutable

object Calculations {
  case class Goods(name: String, price: BigDecimal)

  val inventory: Map[String, Goods] = Map(
    "Soup" -> Goods("Soup", 0.65),
    "Bread" -> Goods("Bread", 0.80),
    "Milk" -> Goods("Milk", 1.30),
    "Apples" -> Goods("Apples", 1.00)
  )

  private val offers: Map[String, (Int, BigDecimal)] = Map(
    "Apples" -> (1, 0.10),
    "Soup" -> (2, 0.50)
  )

  def subTotalCalculation(basket: List[String]): BigDecimal = {
    basket.map(goods => inventory(goods).price).sum
  }

  def discountCalculation(basket: List[String]): mutable.Map[String, BigDecimal] = {

    val discounts = mutable.Map.empty[String, BigDecimal].withDefaultValue(BigDecimal(0))

    val appleDiscount = offers.get("Apples")
    val soupDiscount = offers.get("Soup")

    val soupCount = basket.count(_ == "Soup")
    val breadCount = basket.count(_ == "Bread")

    discounts("Apples") = basket.count(_ == "Apples") * inventory("Apples").price * appleDiscount.get._2

    if (breadCount > 0) {
      val soupTins = soupDiscount.get._1
      val breadDiscount = inventory("Bread").price * soupDiscount.get._2
      val eligibleSoupTins = soupCount / soupTins
      discounts("Bread") = Math.min(eligibleSoupTins, breadCount) * breadDiscount
    }

    discounts
  }

  def priceBasket(basket: List[String]): (BigDecimal, mutable.Map[String, BigDecimal], BigDecimal) = {
    val subTotal = subTotalCalculation(basket)
    val discounts = discountCalculation(basket)
    val total = subTotal - discounts.values.sum
    (subTotal, discounts, total)
  }
}






