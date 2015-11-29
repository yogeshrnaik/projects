The following line was mentioned in the assignment description:
You should use your professional experience to determine a type.

In order to decide the property expected type, following two approaches were thought of.

Approach 1:
============
Try to convert the value of the property to a Boolean, Integer, Double and Aws's Region in the order.
E.g. if property is not boolean the conversion to boolean will fail. 
In that case, the next conversion is to try to convert it to Integer and if that fails to Double and so on.
This chain of converters logic is implemented in ConverterChain class.

Drawback of this approach:
- In case a property's value is wrongly specified then that won't be detected by this solution.
E.g. if for boolean property a double value is mentioned then this approach won't be able to detect that.


Approach 2:
============
Other approach to convert a property's value to an expected type is to maintain a metadata 
of all the properties that have some special type. E.g. 
aws_region_id=aws_region
aws_account_id=integer
job.timeout=integer
score.factor=double

Then map the value of the above metadata to a converter class. This logic is implemented in ConverterChainBasedonKey class.

Advantage of this approach:
- In case a property's value is wrongly specified then that will be detected by this solution.
E.g. if for boolean property a double value is mentioned then this approach will detect it and will mark the property as missing/invalid.


Extending the solution:
========================
Both the classes ConverterChain and ConverterChainBasedonKey implement PropertyConverter interface.
So, this overall approach can be extended by simply adding any new implementation of PropertyConverter interface.
