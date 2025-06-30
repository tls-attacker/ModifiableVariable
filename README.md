# Modifiable Variables

![licence](https://img.shields.io/badge/License-Apachev2-brightgreen.svg)

Modifiable Variable allows you to set modifications to basic types after or before their values are actually determined. When their actual values are determined and you access the value via getters, the original value will be returned in a modified form according to the defined modifications.

# Installation

In order to compile and use ModifiableVariable, you need to have Java and Maven installed. On Ubuntu you can install
Maven by running:

```bash
$ sudo apt-get install maven
```

ModifiableVariable currently needs Java JDK 21 to run. If you have the correct Java version you can install
ModifiableVariable as follows:

```bash
$ git clone https://github.com/tls-attacker/ModifiableVariable.git
$ cd ModifiableVariable
$ mvn clean install
```

If you want to use this project as a dependency, you do not have to compile it yourself. You can find the latest version on [Maven Central](https://central.sonatype.com/artifact/de.rub.nds/modifiable-variable/overview).

# Usage

The best way to present the functionality of ModifiableVariables is by means of a simple example:

```java
ModifiableInteger i = new ModifiableInteger();
i.setOriginalValue(30);
VariableModification<Integer> modifier = new IntegerAddModification(20);
i.setModifications(modifier);
System.out.println(i.getValue());  // 50
```

In this example, we defined a new ModifiableInteger and set its value to 30. Next, we defined a new IntegerAddModification which simply returns a sum of two integers. We set the summand to 20. If we execute the above program, the result 50 is printed.

You can use further modifications to an integer value, for example subtract, xor, or shift. The available modification types depend on the data type you're working with.

## Multiple Modifications

You can apply multiple modifications in sequence:

```java
ModifiableInteger i = new ModifiableInteger();
i.setOriginalValue(10);
i.addModification(new IntegerAddModification(5));
i.addModification(new IntegerMultiplyModification(2));
System.out.println(i.getValue());  // (10 + 5) * 2 = 30
```

## Byte Array Modifications

For byte arrays, you can use various modifications like shuffling, inserting, or XORing bytes:

```java
ModifiableByteArray ba = new ModifiableByteArray();
ba.setOriginalValue(new byte[]{1, 4});
VariableModification<byte[]> modifier = new ByteArrayInsertValueModification(new byte[] {2, 3}, 1);
ba.setModifications(modifier);
System.out.println(ArrayConverter.bytesToHexString(ba.getValue())); // 01 02 03 04
```

# Supported data types

The following modifiable variables are provided in this package with their modifications:

* ModifiableBigInteger: add, explicitValue, multiply, shiftLeft, shiftRight, subtract, xor
* ModifiableBoolean: explicitValue, toggle
* ModifiableByteArray: appendValue, delete, duplicate, explicitValue, insertValue, prependValue, shuffle, xor
* ModifiableInteger: add, explicitValue, multiply, shiftLeft, shiftRight, subtract, swapEndian, xor
* ModifiableLong: add, explicitValue, multiply, shiftLeft, shiftRight, subtract, swapEndian, xor
* ModifiableByte: add, explicitValue, subtract, xor
* ModifiableString: appendValue, delete, explicitValue, insertValue, prependValue

# Creating modifications

To create modifiable variables and apply modifications in your Java code, use the appropriate modification classes:

```java
// Integer modifications
VariableModification<Integer> intMod = new IntegerExplicitValueModification(7);
VariableModification<Integer> addMod = new IntegerAddModification(10);
VariableModification<Integer> xorMod = new IntegerXorModification(0xFF);

// BigInteger modifications
VariableModification<BigInteger> bigIntMod = new BigIntegerAddModification(BigInteger.ONE);

// Byte array modifications
VariableModification<byte[]> byteArrayMod = new ByteArrayXorModification(new byte[] {2, 3}, 0);
```

# Modifiable Variables in JSON

Modifiable variables are serializable into JSON using Jackson. You can use the following code to do that:

```java
ModifiableByteArray mba = new ModifiableByteArray();
mba.setOriginalValue(new byte[]{1, 2, 3});

// Create a Jackson object mapper for serialization
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new ModifiableVariableModule());
mapper.setVisibility(ModifiableVariableModule.getFieldVisibilityChecker());
        
// Serialize the array into JSON
String jsonString = mapper.writeValueAsString(mba);
System.out.println(jsonString);

// Use the JSON to create a modifiable byte array variable again
ModifiableByteArray test = mapper.readValue(jsonString, ModifiableByteArray.class);
System.out.println(DataConverter.bytesToHexString(test.getValue()));
```

When creating the object mapper, make sure to register an instance of the `ModifiableVariableModule` and set the
visibility to `ModifiableVariableModule.getFieldVisibilityChecker()` to ensure proper (de-)serialization.
When using custom modification or variable classes, you will need to register the corresponding classes with the object
mappers' `registerSubtypes` method. All classes that are part of the ModifiableVariable package are already registered in the `ModifiableVariableModule`.

The result of the serialized modifiable byte array looks as follows:

```json
{
  "@type": "ModifiableByteArray",
  "originalValue": "010203"
}
```

If you were to use a byte array with an insertion modification, the result would look as follows:

```json
{
  "@type": "ModifiableByteArray",
  "modifications": [
    {
      "@type": "ByteArrayInsertValueModification",
      "bytesToInsert": "0203",
      "startPosition": 1
    }
  ],
  "originalValue":"010203"
}
```

A schema for the JSON representation of a single `ModifiableVariable` including all standard variable and modification types
can be found in the `src/main/resources` directory. The schema is named `ModifiableVariable.schema.json`.

Below are examples of JSON representations for various modification types.

## Integer Modifications

- Explicit value:

```json
{
  "@type": "IntegerExplicitValueModification",
  "explicitValue": 25872
}
```

- Add:

```json
{
  "@type": "IntegerAddModification",
  "summand": 960
}
```

- Subtract:

```json
{
  "@type": "IntegerSubtractModification",
  "subtrahend": 30959
}
```

- Multiply:

```json
{
  "@type": "IntegerMultiplyModification",
  "factor": 2
}
```

- Right shift:

```json
{
  "@type": "IntegerShiftRightModification",
  "shift": 13
}
```

- Left shift:

```json
{
  "@type": "IntegerShiftLeftModification",
  "shift": 13
}
```

- XOR:

```json
{
  "@type": "IntegerXorModification",
  "xor": 22061
}
```

- Swap endian:

```json
{
  "@type": "IntegerSwapEndianModification"
}
```

## BigInteger Modifications

BigInteger supports similar operations to Integer, for example:

```json
{
  "@type": "BigIntegerAddModification",
  "summand": 1
}
```

```json
{
  "@type": "BigIntegerMultiplyModification",
  "factor": 42
}
```

```json
{
  "@type": "BigIntegerXorModification",
  "xor": 1337
}
```

## Long Modifications

ModifiableLong supports the same operations as Integer, for example:

```json
{
  "@type": "LongAddModification",
  "summand": 10000
}
```

```json
{
  "@type": "LongSwapEndianModification"
}
```

## Byte Array Modifications

- Explicit value:

```json
{
  "@type": "ByteArrayExplicitValueModification",
  "explicitValue": "4F3F8CFC178E660A53DF4D4EE90BD0"
}
```

- XOR:

```json
{
  "@type": "ByteArrayXorModification",
  "xor": "1122",
  "startPosition": 1
}
```

- Insert:

```json
{
  "@type": "ByteArrayInsertValueModification",
  "bytesToInsert": "3D9F3B776503F98A936D94CD7E4AC51B",
  "startPosition": 0
}
```

- Append:

```json
{
  "@type": "ByteArrayAppendValueModification",
  "bytesToAppend": "AABBCC"
}
```

- Prepend:

```json
{
  "@type": "ByteArrayPrependValueModification",
  "bytesToPrepend": "AABBCC"
}
```

- Delete:

```json
{
  "@type": "ByteArrayDeleteModification",
  "count": 2,
  "startPosition": 0
}
```

- Duplicate:

```json
{
  "@type": "ByteArrayDuplicateModification"
}
```

- Shuffle:

```json
{
  "@type": "ByteArrayShuffleModification",
  "shuffle": [ 0, 1 ]
}
```

## Boolean Modifications

- Explicit value:

```json
{
  "@type": "BooleanExplicitValueModification",
  "explicitValue": true
}
```

- Toggle / Negation:

```json
{
  "@type": "BooleanToggleModification"
}
```

## String Modifications

- Explicit value:

```json
{
  "@type": "StringExplicitValueModification",
  "explicitValue": "abc"
}
```

- Append value:

```json
{
  "@type": "StringAppendValueModification",
  "appendValue": "def"
}
```

- Prepend value:

```json
{
  "@type": "StringPrependValueModification",
  "prependValue": "xyz"
}
```

- Insert value:

```json
{
  "@type": "StringInsertValueModification",
  "insertValue": "123",
  "insertPosition": 2
}
```

- Delete:

```json
{
  "@type": "StringDeleteModification",
  "count": 2,
  "startPosition": 1
}
```

