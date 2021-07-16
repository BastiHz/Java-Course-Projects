## Sorting Tool

A simple command line app for sorting inputs. It supports natural sorting and 
sorting by count. Natural means numbers are sorted such that 1 < 5 < 10 instead 
of 1 < 10 < 5, which would be alphabetical. Inputs can be given via the 
console or a text file. Same goes for the outputs.

### Command line arguments
- `-sortingType` followed by either `natural` or `byCount`.
- `-dataType` followed by one of `word`, `long`, `line`.
- `-inputFile` followed by a name of the inputfile containing the data to sort.
- `-outputFile` followed by the name of the outputfile.

It defaults to `-sortingType natural -dataType word` when no arguments are given. 

### Example usages
If no inputfile was specified, the program waits for data to be entered into the 
console. Data can be entered separated by spaces or line breaks, except for 
`-dataType line`, which requires line breaks. To end the data entry 
send a line break followed by an EOF (Ctrl + D on Linux or Ctrl + Z on Windows).

Sorting numbers naturally:
```
> ./gradlew run --args="-dataType long"
> 2 1 10 9 100 9999

Total numbers: 6.
Sorted data: 1 2 9 10 100 9999
```

Sorting words by count:
```
> ./gradlew run --args="-sortingType byCount"
> a b c b a c c a a a

Total words: 10.
b: 2 time(s), 20%
c: 3 time(s), 30%
a: 5 time(s), 50%
```

Sorting lines by count:
```
> ./gradlew run --args="-sortingType byCount -dataType line"
> Hello, world!
> This is a sentence.
> Hello, world!
> This is another sentence.

Total lines: 4.
This is a sentence.: 1 time(s), 25%
This is another sentence.: 1 time(s), 25%
Hello, world!: 2 time(s), 50%
```
