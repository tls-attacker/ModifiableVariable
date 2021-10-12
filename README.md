# Modifiable Variables

![licence](https://img.shields.io/badge/License-Apachev2-brightgreen.svg)
[![jenkins](https://hydrogen.cloud.nds.rub.de/buildStatus/icon.svg?job=ModifiableVariable)](https://hydrogen.cloud.nds.rub.de/job/ModifiableVariable/)

Modifiable variable allows one to set modifications to basic types after or before their values are actually determined. When their actual values are determined and one tries to access the value via getters, the original value will be returned in a modified form accordingly.

# Installation

In order to compile and use ModifiableVariable, you need to have Java and Maven installed. On Ubuntu you can install
 Maven by running:
```bash
$ sudo apt-get install maven
```
ModifiableVariable currently needs Java JDK 8 to run. If you have the correct Java version you can install
 ModifiableVariable as follows.
```bash
$ git clone https://github.com/tls-attacker/ModifiableVariable.git
$ cd ModifiableVariable
$ mvn clean install
```

If you want to use this project as a dependency, you do not have to compile it yourself and can include it in your pom
.xml as follows.

```xml
<dependency>
    <groupId>de.rub.nds</groupId>
    <artifactId>ModifiableVariable</artifactId>
    <version>3.1.0</version>
</dependency>
```

# Usage

The best way to present the functionality of ModifiableVariables is by means of a simple example:

```java
ModifiableInteger i = new ModifiableInteger();
i.setOriginalValue(30);
VariableModification<Integer> modifier = IntegerModificationFactory.add(20);
i.setModification(modifier);
System.out.println(i.getValue());  // 50
```

In this example, we defined a new ModifiableInteger and set its value to 30. Next, we defined a new modification AddModification which simply returns a sum of two integers. We set its value to 20. If we execute the above program, the result 50 is printed. 

You can use further modifications to an integer value, for example subtract, xor or shift.

In byte arrays you can use further modifications like shuffling or inserting bytes:

```java
ModifiableByteArray ba = new ModifiableByteArray();
VariableModification<byte[]> modifier = ByteArrayModificationFactory.insert(new byte[] {2, 3}, 1);
ba.setOriginalValue(new byte[]{1, 4});
ba.setModification(modifier);
System.out.println(ArrayConverter.bytesToHexString(ba)); // 01 02 03 04
```

# Supported data types
The following modifiable variables are provided in this package with their modifications:
* ModifiableBigInteger: add, explicitValue, shiftLeft, shiftRight, subtract, xor
* ModifiableBoolean: explicitValue, toggle
* ModifiableByteArray: delete, duplicate, explicitValue, insert, shuffle, xor
* ModifiableInteger: add, explicitValue, shiftLeft, shiftRight, subtract, xor
* ModifiableLong: add, explicitValue, subtract, xor
* ModifiableByte: add, explicitValue, subtract, xor
* ModifiableString: explicitValue

# Creating modifications
If you use a modifiable variables in your Java code, use the modification factories, for example:
```java
VariableModification<Integer> modifier = IntegerModificationFactory.explicitValue(7);
VariableModification<BigInteger> modifier = BigIntegerModificationFactory.add(BigInteger.ONE);
VariableModification<byte[]> modifier = ByteArrayModificationFactory.xor(new byte[] {2, 3}, 0);
```

# Modifiable variables in XML
Modifiable variables are serializable with JAXB into XML. You can use the following code to do that:

```java
ModifiableByteArray mba = new ModifiableByteArray();
mba.setOriginalValue(new byte[]{1, 2, 3});
StringWriter writer = new StringWriter();

// we have to create a jaxb context a put there all the classes we are going to use for serialization
JAXBContext context = JAXBContext.newInstance(ModifiableByteArray.class, ByteArrayDeleteModification.class,
                ByteArrayExplicitValueModification.class, ByteArrayInsertModification.class,
                ByteArrayXorModification.class);
Marshaller m = context.createMarshaller();
m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
// we marshall the array into xml
m.marshal(mba, writer);
String xmlString = writer.toString();
System.out.println(xmlString);

// we can use the xml to create a modifiable byte array variable again
Unmarshaller um = context.createUnmarshaller();
ModifiableByteArray test = (ModifiableByteArray) um.unmarshal(new StringReader(xmlString));
System.out.println(ArrayConverter.bytesToHexString(test));
```

The result of the serialized modifiable byte array looks as follows:

```xml
<modifiableByteArray>
    <originalValue>01 02 03</originalValue>
</modifiableByteArray>
```

If you would use modification from the previous example, the result would look as follows:
```xml
<modifiableByteArray>
    <originalValue>01 02 03</originalValue>
    <ByteArrayInsertModification>
        <bytesToInsert>02 03</bytesToInsert>
        <startPosition>1</startPosition>
    </ByteArrayInsertModification>
</modifiableByteArray>
```

The following examples should give you a useful list of modifications in modifiable variables:

## Integer
- Explicit value:
```xml
    <IntegerExplicitValueModification>
        <explicitValue>25872</explicitValue>
    </IntegerExplicitValueModification>
```

- Subtract:
```xml
    <IntegerSubtractModification>
        <subtrahend>30959</subtrahend>
    </IntegerSubtractModification>
```

- Add:
```xml
    <IntegerAddModification>
        <summand>960</summand>
    </IntegerAddModification>
```

- Right shift:
```xml
    <IntegerShiftRightModification>
        <shift>13</shift>
    </IntegerShiftRightModification>
```

- Left shift:
```xml
    <IntegerShiftLeftModification>
        <shift>13</shift>
    </IntegerShiftLeftModification>
```

- XOR:
```xml
    <IntegerXorModification>
        <xor>22061</xor>
    </IntegerXorModification>
```

You can use the same operations for BigInteger data types, for example:
```xml
    <BigIntegerAddModification>
        <summand>1</summand>
    </BigIntegerAddModification>
```
ModifiableLong and ModifiableBytes support the following operations: add, explicitValue, subtract, xor

## Byte Array
- Explicit value:
```xml
    <ByteArrayExplicitValueModification>
        <explicitValue>
            4F 3F 8C FC 17 8E 66 0A  53 DF 4D 4E E9 0B D0
        </explicitValue>
    </ByteArrayExplicitValueModification>
```

- XOR:
```xml
    <ByteArrayXorModification>
        <xor>11 22</xor>
        <startPosition>1</startPosition>
    </ByteArrayXorModification>
```

- Insert:
```xml
    <ByteArrayInsertModification>
        <bytesToInsert>
            3D 9F 3B 77 65 03 F9 8A  93 6D 94 CD 7E 4A C5 1B 
        </bytesToInsert>
        <startPosition>0</startPosition>
    </ByteArrayInsertModification>
```

- Delete: 
```xml
    <ByteArrayDeleteModification>
        <count>2</count>
        <startPosition>0</startPosition>
    </ByteArrayDeleteModification>
```

- Shuffle:
```xml
    <ByteArrayShuffleModification>
        <shuffle>02 03</shuffle>
    </ByteArrayShuffleModification>
```

# Boolean
- Explicit value:
```xml
    <BooleanExplicitValueModification>
        <explicitValue>true</explicitValue>
    </BooleanExplicitValueModification>
```

- Toggle:
```xml
    <BooleanToggleModification/>
```

# String
- Explicit value:
```xml
    <StringExplicitValueModification>
        <explicitValue>abc</explicitValue>
    </StringExplicitValueModification>
```
