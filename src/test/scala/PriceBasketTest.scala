package org.basket

import Calculations._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.io.{ByteArrayOutputStream, PrintStream}

class PriceBasketTest extends AnyFlatSpec with Matchers {

  "SubTotalCalculation" should "return total amount before discounts" in {
    val subtotal = subTotalCalculation(List("Apples", "Milk", "Soup"))
    subtotal should be(2.95)

    val subtotal1 = subTotalCalculation(List("Apples", "Soup", "Milk", "Soup", "Bread"))
    subtotal1 should be(4.40)
  }

  "DiscountCalculation" should "return correct discounts for different items" in {
    val discounts1 = discountCalculation(List("Apples", "Milk", "Bread")).values.sum
    discounts1 should be(0.1)

    val discounts2 = discountCalculation(List("Apples", "Milk", "Soup", "Soup", "Bread")).values.sum
    discounts2 should be(0.5)

    val discounts3 = discountCalculation(List("Milk", "Bread")).values.sum
    discounts3 should be(0.0)

    val discounts4 = discountCalculation(List("Apples", "Apples", "Milk", "Bread")).values.sum
    discounts4 should be(0.2)

    val discounts5 = discountCalculation(List("Apples", "Apples", "Soup", "Soup", "Soup", "Soup", "Milk", "Bread", "Bread")).values.sum
    discounts5 should be(1.0)

  }

  "priceBasket" should "return correct results for different basket items" in {
    val basket = List("Apples", "Milk", "Bread")
    val (subtotal, discounts, total) = priceBasket(basket)
    subtotal should be(3.10)
    discounts.values.sum should be(0.1)
    total should be(3.0)

    val basket1 = List("Apples", "Apples", "Soup", "Soup", "Soup", "Soup", "Soup", "Soup", "Soup", "Soup", "Milk", "Bread", "Bread", "Bread")
    val (subtotal1, discounts1, total1) = priceBasket(basket1)
    subtotal1 should be(10.9)
    discounts1.values.sum should be(1.4)
    total1 should be(9.5)
  }

  "main" should "return correct values with discount for two apples four soup and two breads" in {
    val basket = Array("Apples", "Soup", "Apples", "Soup", "Soup", "Soup", "Bread", "Bread")
    val outputStream = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(outputStream)) {
      PriceBasket.main(basket)
    }
    val output = outputStream.toString.trim
    val expectedOutput =
      """Subtotal: £6.20
        |Apples 10% off: £0.20
        |Bread 50% off: £0.80
        |Total price: £5.20""".stripMargin

    output shouldBe expectedOutput
  }
  "main" should "return correct values with discount for three soup one bread and one milk " in {
    val outputStream = new ByteArrayOutputStream()
    val basket1 = Array("Soup", "Soup", "Soup", "Bread", "Milk")
    Console.withOut(new PrintStream(outputStream)) {
      PriceBasket.main(basket1)
    }
    val output1 = outputStream.toString.trim
    val expectedOutput1 =
      """Subtotal: £4.05
        |Bread 50% off: £0.40
        |Total price: £3.65""".stripMargin

    output1 shouldBe expectedOutput1
  }

  "main" should "output an error message for unknown items in the basket" in {
    val outputStream = new ByteArrayOutputStream()
    val exception = intercept[RuntimeException] {

      Console.withOut(new PrintStream(outputStream)) {

        PriceBasket.main(Array("Apples", "Milk", "UnknownItem"))
      }
    }
    exception.getMessage should be("Basket Contains unknown Items => UnknownItem")
  }
}
