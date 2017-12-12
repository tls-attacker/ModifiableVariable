# Modifiable Variables

![licence](https://img.shields.io/badge/License-Apachev2-brightgreen.svg)
[![travis](https://travis-ci.org/RUB-NDS/ModifiableVariable.svg?branch=master)](https://travis-ci.org/RUB-NDS/ModifiableVariable)

Modifiable variable allows one to set modifications to basic types after or before their values are actually determined. When their actual values are determined and one tries to access the value via getters, the original value will be returned in a modified form accordingly.

The best way to present the functionality of this concept is by means of a simple example:

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

If you want to use modifiable variables in your maven projects, you can include the following dependency in your pom file:
```xml
<dependency>
  <groupId>de.rub.nds</groupId>
  <artifactId>ModifiableVariable</artifactId>
  <version>2.2</version>
</dependency>
```

# Supported data types
The following modifiable variables are provided in this package with their modifications:
* ModifiableBigInteger: add, explicitValue, shiftLeft, shiftRight, subtract, xor
* ModifiableBoolean: explicitValue, toogle
* ModifiableByteArray: delete, duplicate, explicitValue, insert, suffle, xor
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
    <byteArrayInsertModification>
        <bytesToInsert>02 03</bytesToInsert>
        <startPosition>1</startPosition>
    </byteArrayInsertModification>
</modifiableByteArray>
```

The following examples should give you a useful list of modifications in modifiable variables:

## Integer
- Explicit value:
```xml
    <integerExplicitValueModification>
        <explicitValue>25872</explicitValue>
    </integerExplicitValueModification>
```

- Subtract:
```xml
    <integerSubtractModification>
        <subtrahend>30959</subtrahend>
    </integerSubtractModification>
```

- Add:
```xml
    <integerAddModification>
        <summand>960</summand>
    </integerAddModification>
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

You can use the same operations for BigInteger data types, for example:
```xml
    <bigIntegerAddModification>
        <summand>1</summand>
    </bigIntegerAddModification>
```
ModifiableLong and ModifiableBytes support the following operations: add, explicitValue, subtract, xor

## Byte Array
- Explicit value:
```xml
    <byteArrayExplicitValueModification>
        <explicitValue>
            4F 3F 8C FC 17 8E 66 0A  53 DF 4D 4E E9 0B D0
        </explicitValue>
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
    <byteArrayInsertModification>
        <bytesToInsert>
            3D 9F 3B 77 65 03 F9 8A  93 6D 94 CD 7E 4A C5 1B 
        </bytesToInsert>
        <startPosition>0</startPosition>
    </byteArrayInsertModification>
```

- Delete: 
```xml
    <byteArrayDeleteModification>
        <count>2</count>
        <startPosition>0</startPosition>
    </byteArrayDeleteModification>
```

- Shuffle:
```xml
    <byteArrayShuffleModification>
        <shuffle>02 03</shuffle>
    </byteArrayShuffleModification>
```

# Boolean
- Explicit value:
```xml
    <booleanExplicitValueModification>
        <explicitValue>true</explicitValue>
    </booleanExplicitValueModification>
```

- Toogle:
```xml
    <booleanToogleModification/>
```

# String
- Explicit value:
```xml
    <stringExplicitValueModification>
        <explicitValue>abc</explicitValue>
    </stringExplicitValueModification>
```
