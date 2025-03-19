# Modifiable Variables

![licence](https://img.shields.io/badge/License-Apachev2-brightgreen.svg)
[![jenkins](https://hydrogen.cloud.nds.rub.de/buildStatus/icon.svg?job=ModifiableVariable)](https://hydrogen.cloud.nds.rub.de/job/ModifiableVariable/)

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

# Modifiable variables in XML

Modifiable variables are serializable with Jakarta XML Binding (JAXB) into XML. You can use the following code to do that:

```java
ModifiableByteArray mba = new ModifiableByteArray();
mba.setOriginalValue(new byte[]{1, 2, 3});
StringWriter writer = new StringWriter();

// Create a JAXB context with all classes you'll need for serialization
JAXBContext context = JAXBContext.newInstance(
        ModifiableByteArray.class, 
        ByteArrayDeleteModification.class,
        ByteArrayExplicitValueModification.class, 
        ByteArrayInsertValueModification.class,
        ByteArrayXorModification.class
);

// Create a marshaller with formatted output
Marshaller m = context.createMarshaller();
m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
// Marshal the array into XML
m.marshal(mba, writer);
String xmlString = writer.toString();
System.out.println(xmlString);

// Use the XML to create a modifiable byte array variable again
Unmarshaller um = context.createUnmarshaller();
ModifiableByteArray test = (ModifiableByteArray) um.unmarshal(new StringReader(xmlString));
System.out.println(ArrayConverter.bytesToHexString(test.getValue()));
```

When creating the JAXBContext, make sure to include all modification classes you intend to use. The example above only includes a subset of the available modifications.

The result of the serialized modifiable byte array looks as follows:

```xml
<modifiableByteArray>
    <originalValue>01 02 03</originalValue>
</modifiableByteArray>
```

If you were to use a byte array with an insertion modification, the result would look as follows:

```xml
<modifiableByteArray>
    <originalValue>01 02 03</originalValue>
    <modifications>
        <byteArrayInsertValueModification>
            <bytesToInsert>02 03</bytesToInsert>
            <startPosition>1</startPosition>
        </byteArrayInsertValueModification>
    </modifications>
</modifiableByteArray>
```

Below are examples of XML representations for various modification types:

## Integer Modifications

- Explicit value:

```xml
<integerExplicitValueModification>
    <explicitValue>25872</explicitValue>
</integerExplicitValueModification>
```

- Add:

```xml
<integerAddModification>
    <summand>960</summand>
</integerAddModification>
```

- Subtract:

```xml
<integerSubtractModification>
    <subtrahend>30959</subtrahend>
</integerSubtractModification>
```

- Multiply:

```xml
<integerMultiplyModification>
    <factor>2</factor>
</integerMultiplyModification>
```

- Right shift:

```xml
<integerShiftRightModification>
    <shift>13</shift>
</integerShiftRightModification>
```

- Left shift:

```xml
<integerShiftLeftModification>
    <shift>13</shift>
</integerShiftLeftModification>
```

- XOR:

```xml
<integerXorModification>
    <xor>22061</xor>
</integerXorModification>
```

- Swap endian:

```xml
<integerSwapEndianModification/>
```

## BigInteger Modifications

BigInteger supports similar operations to Integer:

```xml
<bigIntegerAddModification>
    <summand>1</summand>
</bigIntegerAddModification>
```

```xml
<bigIntegerMultiplyModification>
    <factor>42</factor>
</bigIntegerMultiplyModification>
```

```xml
<bigIntegerXorModification>
    <xor>1337</xor>
</bigIntegerXorModification>
```

## Long Modifications

ModifiableLong supports the same operations as Integer:

```xml
<longAddModification>
    <summand>10000</summand>
</longAddModification>
```

```xml
<longSwapEndianModification/>
```

## Byte Array Modifications

- Explicit value:

```xml
<byteArrayExplicitValueModification>
    <explicitValue>4F 3F 8C FC 17 8E 66 0A 53 DF 4D 4E E9 0B D0</explicitValue>
</byteArrayExplicitValueModification>
```

- XOR:

```xml
<byteArrayXorModification>
    <xor>11 22</xor>
    <startPosition>1</startPosition>
</byteArrayXorModification>
```

- Insert:

```xml
<byteArrayInsertValueModification>
    <bytesToInsert>3D 9F 3B 77 65 03 F9 8A 93 6D 94 CD 7E 4A C5 1B</bytesToInsert>
    <startPosition>0</startPosition>
</byteArrayInsertValueModification>
```

- Append:

```xml
<byteArrayAppendValueModification>
    <bytesToAppend>AA BB CC</bytesToAppend>
</byteArrayAppendValueModification>
```

- Prepend:

```xml
<byteArrayPrependValueModification>
    <byteArrayToPrepend>AA BB CC</byteArrayToPrepend>
</byteArrayPrependValueModification>
```

- Delete:

```xml
<byteArrayDeleteModification>
    <count>2</count>
    <startPosition>0</startPosition>
</byteArrayDeleteModification>
```

- Duplicate:

```xml
<byteArrayDuplicateModification/>
```

- Shuffle:

```xml
<byteArrayShuffleModification>
    <shuffle>00 01</shuffle>
</byteArrayShuffleModification>
```

## Boolean Modifications

- Explicit value:

```xml
<booleanExplicitValueModification>
    <explicitValue>true</explicitValue>
</booleanExplicitValueModification>
```

- Toggle:

```xml
<booleanToggleModification/>
```

## String Modifications

- Explicit value:

```xml
<stringExplicitValueModification>
    <explicitValue>abc</explicitValue>
</stringExplicitValueModification>
```

- Append value:

```xml
<stringAppendValueModification>
    <appendValue>def</appendValue>
</stringAppendValueModification>
```

- Prepend value:

```xml
<stringPrependValueModification>
    <prependValue>xyz</prependValue>
</stringPrependValueModification>
```

- Insert value:

```xml
<stringInsertValueModification>
    <insertValue>123</insertValue>
    <insertPosition>2</insertPosition>
</stringInsertValueModification>
```

- Delete:

```xml
<stringDeleteModification>
    <count>2</count>
    <startPosition>1</startPosition>
</stringDeleteModification>
```

