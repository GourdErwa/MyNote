
`
//support [ Replace with collect ]
private List<Integer> collect(List<Integer> list) {
        //use JDK create List
        final List<Integer> newList = new ArrayList<>();
        for (Integer integer : list) {
            if (integer > 5) {
                newList.add(integer);
            }
        }
        return newList;
    }
`
`
//nonsupport [ Replace with collect ]
private List<Integer> collect(List<Integer> list) {
        //use Guava create List
        final List<Integer> newList = Lists.newArrayList();
        for (Integer integer : list) {
            if (integer > 5) {
                newList.add(integer);
            }
        }
        return newList;
    }
`

The Google Guava Toolkit (https://github.com/google/guava) is widely used now.
We hope that later versions will support something like (https://github.com/google/guava/wiki/CollectionUtilitiesExplained)
`
List <TypeThatsTooLongForItsOwnGood> list = Lists.newArrayList ();
Map <KeyType, LongishValueType> map = Maps.newLinkedHashMap ();
`
and refactoring check of replace stream API.
reference upload pictures,thanks

