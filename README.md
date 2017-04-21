# Modifiable Variables
Modifiable variable allows one to set modifications to basic types after or before their values are actually determined. When their actual values are determined and one tries to access the value via getters the original value will be returned in a modified form accordingly.

The best way to present the functionality of this concept is by means of a simple example:

```java
ModifiableInteger i = new ModifiableInteger();
i.setOriginalValue(30);
i.setModification(new AddModification(20));
System.out.println(i.getValue());  // 50
```

In this example, we defined a new ModifiableInteger and set its value to 30. Next, we defined a new modification AddModification which simply returns a sum of two integers. We set its value to 20. If we execute the above program, the result 50 is printed. 

You can use further modifications to an integer value, for example subtract, xor or shift.

# Modifiable variables in Java
If you use a modifiable variable in your Java code, use the modification factories, e.g.:
```java
VariableModification<Integer> modifier = IntegerModificationFactory.explicitValue(7);
VariableModification<BigInteger> modifier = BigIntegerModificationFactory.add(BigInteger.ONE);
VariableModification<byte[]> modifier = ByteArrayModificationFactory.xor(modification1, 0);
VariableModification<byte[]> modifier = ByteArrayModificationFactory.insert(modification1, 0);
```

# Modifiable variables in XML
Modifiable variables are serializable with JAXB into XML.
```xml
      <SomeVariable>
         <integerAddModification>
			<summand>2000</summand>
         </integerAddModification>
      </SomeVariable>
     
```

The following examples should give you a useful list of modifiable variables:

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

## Byte Arrays
- Explicit value:
```xml
    <byteArrayExplicitValueModification>
        <explicitValue>
            4F 3F 8C FC 17 8E 66 0A  53 DF 4D 4E E9 0B D0 B3 
            02 79 74 1F 8B 8A F6 D0  1E AC 59 53 7B 87 DE 89 
            C4 13 28 69 3C 18 F8 3A  C7 3E 30 44 C9 61 D4 
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
Here, we XOR the original value with the xor value, starting with the startPosition:

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
