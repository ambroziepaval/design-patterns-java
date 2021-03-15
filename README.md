## Java Design Patterns

### Builder

When piecewise object construction is complicated, provide an API for doing it succinctly.

### Factory

A component responsibly solely for the wholesale (not piecewise) creation of objects.

### Prototype

A partially or fully initialized object that you copy (clone) and make use of.

### Singleton

A component which is instantiated only once.

### Adapter

A construct which adapts and existing interface X to conform to the required interface Y.

### Bridge

A mechanism that decouples and interface (hierarchy) from an implementation (hierarchy).

### Composite

A mechanism for treating individual (scalar) objects and composition of objects in a uniform manner.

### Decorator

Facilitates the addition of behaviors to individual objects without inheriting from them.

### Facade

Provides a simple, easy to understand user interface over a large and sophisticated body of code.

### Flyweight

A space optimization technique that lets us use less memory by storing the data associated with similar objects externally.

### Proxy

A class that functions as an interface to a particular resource. That resource may be remote, expensive to construct, or may require logging, or some other added functionality.

### Chain of Responsibility

A chain of components who all get a chance to process a command or a query, optionally having default processing implementation, and an ability to terminate the processing chain.

### Command

An object which represents an instruction to perform a particular action. Contains all the information necessary for the action to be taken.

### Interpreter

A component that processes structured text data. Does so by turning it into separate lexical tokens (lexing) and then interpreting sequences of said tokens (parsing).

### Iterator

An object that facilitates the traversal of a data structure.

### Mediator

A component that facilitates communication between other components without them necessarily being aware of each other or having direct (reference) access to each other.

### Memento

A token/handle representing the system state. Lets us roll back to the state when the token was generated. May or may not directly expose state information.

### Null Object

A no-op object that conforms to the required interface, satisfying a dependency requirement of some other object.

### Observer

An observer is an object that wishes to be informed about events happening in the system. The entity generating the events is an observable.

### State

A pattern in which the object's behaviour is determined by its state. An object transitions from one state to another (something needs to _trigger_ a transition).

A formalized construct which manages state and transitions is called a _state machine_.

### Strategy

Enables the exact behavior of a system to be selected either at run-time (dynamic) or compile-time (static).

Also known as a policy (esp. in the C++ world).

### Template Method

Allows us to define the 'skeleton' of the algorithm, with concrete implementations defined in subclasses.

### Visitor

A pattern where a component (visitor) is allowed to traverse the entire inheritance hierarchy. Implemented by propagating a single _visit()_ method throughout the entire hierarchy.

### Thread Pool

The Thread Pool pattern helps to save resources in a multithreaded application, and also to contain the parallelism in certain predefined limits. When you use a thread pool, you
write your concurrent code in the form of parallel tasks and submit them for execution to an instance of a thread pool. This instance controls several re-used threads for executing
these tasks.