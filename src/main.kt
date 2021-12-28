/**
 * Example main function that uses the Randomizer class and instances of
 * `Item` and `ItemWithProps` which both are interfaces of RandomizedItem.
 *
 * @param args command line arguments (unused in this example)
 */
fun main(args: Array<String>) {
  // Initialize the Randomizer with a few items.
  val list = Randomizer<String>(
    arrayOf(
      Item("Cat", 1.0f),
      Item("Dog", 2.5f),
      Item("Salamander", 0.5f),
      ItemWithProps(
        "Zebra", 4.3f, mapOf(
          Pair("stripes", 5),
          Pair("hooves", 4),
        )
      ),
    )
  )

  // Retrieve five weighted random items
  val results = list.next(5)

  // Write the results (and potential properties) to output
  for (r in results) {
    if (r is ItemWithProps<*, *, *>) {
      println("${r.value}")
      r.props.forEach { (k, v) ->
        println("  $k: $v")
      }
    }
    else {
      println(r.value)
    }
  }
}