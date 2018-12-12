// insert some test objects:

db.imageCapture.insertMany([
    {
        date: ISODate("2019-11-25T01:13:00.000Z"),
        lbls: ["one", "two", "three"]
    },
    {
        date: ISODate("2019-11-25T04:23:00.000Z"),
        lbls: ["four"]
    },
    {
        date: ISODate("2019-11-26T03:21:00.000Z"),
        lbls: ["five", "six"]
    },
]);


// count in buckets using Mongo queries
db.imageCapture.aggregate([
    {
        $bucket: {
            groupBy: "$date",
            boundaries: [ISODate("2019-11-25T00:00:00.000Z"), ISODate("2019-11-26T00:00:00.000Z"), ISODate("2019-11-27T00:00:00.000Z"),
                ISODate("2019-11-28T00:00:00.000Z")],
            default: 'nothing',
            output: {
                "count": {"$sum": {"$size": "$lbls"}}
            }

        }
    }
]);

// find total number of labels, to verify that counting is working correctly
db.imageCapture.aggregate([
    {$project: {"foo": {$size: "$lbls"}}},
    {$group: {"_id": null, "count": {$sum: "$foo"}}}
]);

