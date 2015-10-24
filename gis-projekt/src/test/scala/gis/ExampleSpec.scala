package gis

class ExampleSpec extends UnitSpec {

  val example = new Example

  it should "return 3" in {
    example.value should be (3)
  }
}
