import java.util.*

/**
 * The interface defining the type to be randomized. All interfaces conforming
 * to this interface need to at least provide a weight and value. Consider this
 * class to be metadata about the contents to be randomly returned.
 */
interface RandomizedItem<Type> {
  var value: Type
  var weight: Float
}

/**
 * A basic implementation of `RandomizedItem` that can store any one type of
 * object. It supports only a `weight` and `value`.
 *
 * @param weight the weight of the randomized item. This can be any arbitrary
 * floating point number. The significance is relative to other items in the
 * list. If the number is relatively greater, it is more likely to appear. The
 * converse is also true.
 * @param value the actual object to be described by this meta data
 */
class Item<Type> constructor(
  override var value: Type,
  override var weight: Float = 1.0f,
) : RandomizedItem<Type> {}

/**
 * Similar to `Item<Type>`, this implementation provides the ability to store a
 * key-value pair of additional properties for each item being tracked. These are
 * of course, in addition to the weight and value.
 *
 * @param weight the weight of the randomized item. This can be any arbitrary
 * floating point number. The significance is relative to other items in the
 * list. If the number is relatively greater, it is more likely to appear. The
 * converse is also true.
 * @param value the actual object to be described by this meta data
 * @param props a `Map` of additional properties. It defaults to an empty map
 * should those properties not be needed at the time of the objects creation
 */
class ItemWithProps<Type, K, V> constructor(
  override var value: Type,
  override var weight: Float = 1.0f,
  var props: Map<K, V> = HashMap<K, V>(),
) : RandomizedItem<Type> {}

/**
 * Creates what is essentially a list of items to be randomly returned in uneven
 * proportions. It takes any implementation of `RandomizedItem` and uses the
 * weight value of each supplied item in the list to determine whether it should
 * be the item returned when requested.
 *
 * @param items an array of `RandomizedItem` implementation objects
 */
class Randomizer<Type>(items: Array<RandomizedItem<Type>> = emptyArray()) {
  /** The internal list of the items to be randomly distributed */
  private var _items: Array<RandomizedItem<Type>> = emptyArray()

  /** The total weight of all items in the list */
  private var total: Float = 0.0f

  /** An optimization flag that determines whether we need to calculate again */
  private var newItemsSinceLastCount: Boolean = true

  /** A computed getter that resets the flag for calculation */
  var items: Array<RandomizedItem<Type>>
    get() = this._items
    set(value) {
      _items = value
      newItemsSinceLastCount = true
    }

  /** Initializes the internal list of items with the supplied param */
  init {
    this.items = items
  }

  /**
   * Fetch the next n-number of randomly chosen items from the list. It
   * defaults to a list of one item, but more than one can be specified
   * to be fetched.
   *
   * @param count the number of choices to make
   * @return a list of `RandomizedItem` objects
   */
  fun next(count: Int = 1): Collection<RandomizedItem<Type>> {
    val results = ArrayList<RandomizedItem<Type>>()
    val rand = Random()

    for (times in 0 until count) {
      calcTotals()

      var sum = 0.0f
      val number = rand.nextFloat() * total
      for (entry in items) {
        sum += entry.weight
        if (number <= sum) {
          results.add(entry)
          break
        }
      }
    }

    return results
  }

  /**
   * Calculates the total sum of all the weights of the items in the list.
   */
  private fun calcTotals(): Float {
    if (newItemsSinceLastCount) {
      this.total = 0.0f

      for (entry in items) {
        total += entry.weight
      }

      newItemsSinceLastCount = false
    }

    return this.total
  }
}