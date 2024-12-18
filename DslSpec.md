# DSL (Domain Specific Language) for Object Modeling

## Background
The goal here is to define a DSL to describe objects, and their relations to other objects in a language and framework neutral fashion. The goal is to gather meta information in the support of efforts to 

## Terminology

### Stereotypes
Whereas the idea is to strongly type the object definition, the concept of applying stereotypes to entities, attributes, and object associations comes into play as when we associate this meta level information, we provide hints to the generation module to make certain assumptions and make decisions accordingly. 

### Entities
A cornerstone concept is the 'Entity'. As the name implies, this is a reference to an object. This is a first class actor, usually backed by a table.



**entityName**

User friendly name for the entity.

**tableName**

Defines the target where the entity will be 
	persisted to.

**description**

Optional.

### Collections



#### Entity stereotypes
Entities may assume the following stereo types.

**TopLevel**

Defines a first class actor.

**Dependant**
An object which 'belongs' to a parent entity.

**Reference**
Indicates this actor is referenced by other objects, by default in a unidirectional fashion.


### Entity Attributes
Entities are composed of attributes. These are the equivelant of column defintions for a table.

At a minimum, only a name property is required. If additional details are omitted, then expected behavior is that the atribute will be considered to assume a string (Varchar) type with a default max length of 75* characters.

#### Attribute stereotypes
As with entities, attributes may have associated stereo types


### Entity collections
In addition to attribute collections, entities have the ability to contain child collections of other entities. For the current implementation, all delete operations are implemented where the delete srategy is implemented in a cascading fashion. Future releases will provide additional options.

An example of a collection definition would be where a shopping cart entity would have a collection of associated line items.

### Entity associations
Whereas collections imply a bidirectional association between two objects, unidirectional associations exist as well. An example would be an product which has a reference to an product category. Associations are currently supported in the unidirectional aspect at this time. Future support for bi directional is planned. 

### Object graphs
Object graphs refer to a series of associations between entities. 

## Future Enhancements
Initial effort was timeboxed, so there are a few areas for improvement!

* Support for many to many relations
* 

