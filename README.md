# WeightedRandoms-Kotlin
An implementation of Weighted Randoms using Kotlin

## Description
The WeightedRandoms series was originally made to help me 
with some role-playing games I was working on. I wanted to 
randomly choose an item from a list but to do so with unequal
chances that the item I wanted would be returned. For example
if I wanted to determine a list of NPCs in a village of
Amazons, the chance that each NPC would be female is more likely
than if they were to somehow be men.

In this case, I might write some code like this

```kotlin
  val list = Randomizer<String>(
    arrayOf(
      Item("Male", 1.0f),
      Item("Female", 4.5f),
    )
  )

  // Retrieve five weighted random items
  list.next(5)
    .let { it.map { s -> s.value } }
    .also { println(it) }


  // Might return logical results like these
  // ["Male", "Female", "Female", "Female", "Male"]
```