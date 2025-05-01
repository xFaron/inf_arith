# **Arbitrary Precision Arithmetic Library**

An easy-to-use Java library for performing arbitrary-precision integer and floating‑point arithmetic, complete with a built-in testing framework and Ant-based build system.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
  - [Build and Test](#build-and-test)
  - [Generate JAR](#generate-jar)
  - [Run the Application](#run-the-application)
- [Folder Structure](#folder-structure)
- [Usage](#usage)
- [Testing Framework](#testing-framework)
- [License](#license)
- [Authors](#authors)


## Features

- **AInteger** – High‑precision integer arithmetic class.
- **AFloat** – High‑precision floating‑point arithmetic class.
- **ANumber** – Base abstract class.
- Simple command-line tester (`Tester.java`).
- Ant build setup with targets for compile, test, JAR, and cleanup.

## Prerequisites

- [Java JDK 8+](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
- [Apache Ant](https://ant.apache.org/)
- [Python 3](https://www.python.org/downloads/) (for cleaning test cases)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/xFaron/inf_arith.git
cd inf_arith
```

### Build and Test

1. **Clean previous builds and generated directories**
   ```bash
   ant clean
   ```

2. **Compile source and test classes**
   ```bash
   ant test
   ```
   This target will run all pre-set tests via the `Tester` class.
   Details regarding testing can be found [here](#testing-framework)

### Generate JAR

To compile sources and package the library into a JAR file:

```bash
ant jar
```
The resulting `MyInfArith.jar` will be placed in the `dist/` directory.

### Run the Application

To execute the JAR:

```bash
ant run-jar
```


## Folder Structure

```text
.
├── build/                # Compiled `.class` files
│   ├── arbritaryarithmetic
│   │   ├── AFloat.class
│   │   ├── AInteger.class
│   │   └── ANumber.class
│   ├── MyInfArith.class
│   └── Tester.class
├── dist/                 # Generated artifacts (JAR)
├── src/                  # Java source files
│   ├── arbritaryarithmetic
│   │   ├── AFloat.java
│   │   ├── AInteger.java
│   │   └── ANumber.java
│   └── MyInfArith.java   # Main class
├── test/                 # Test cases and test harness
│   ├── input/            # Public test inputs
│   │   └── public_testcases.txt
│   ├── old_tests/        # Legacy test sets
│   │   ├── input/
│   │   │   ├── 1.txt
│   │   │   └── ...
│   │   └── output/
│   │       ├── 1.txt
│   │       └── ...
│   ├── output/           # Public test outputs
│   │   └── public_testcases.txt
│   ├── Tester.java       # Test runner
│   └── transfer_valid_cases.py # Utility to clean/transfer test cases
├── build.xml             # Ant build script
└── README.md             
```

## Usage

Import the `AInteger` and `AFloat` classes in your Java project to start performing high‑precision calculations:

```java
import arbritaryarithmetic.AInteger;
import arbritaryarithmetic.AFloat;

public class Example {
    public static void main(String[] args) {
        AInteger bigInt = new AInteger("12345678901234567890");
        AFloat bigFloat = new AFloat("3.14159265358979323846");

        AInteger sum = bigInt.add(new AInteger("98765432109876543210"));
        AFloat product = bigFloat.multiply(new AFloat("2.0"));

        System.out.println("Sum: " + sum.toString());
        System.out.println("Product: " + product.toString());
    }
}
```

## Testing Framework

- **`Tester.java`** runs all compiled test cases.
- Public test cases are located under `test/input` and `test/output`.
- **`transfer_valid_cases.py`** cleans test case files once all the test cases are run successfully. These test cases are then saved in `test/old_tests`.

To re-generate and clean test cases:
```bash
ant test_clean
```

## License

Distributed under the MIT License. See `LICENSE` for more details.

## Authors

- **Harikrishna S** – [xFaron](https://github.com/xFaron)
