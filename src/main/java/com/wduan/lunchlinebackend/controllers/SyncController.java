package com.wduan.lunchlinebackend.controllers;

import com.mongodb.client.model.Updates;
import com.wduan.lunchlinebackend.LogController;
import com.wduan.lunchlinebackend.helpers.dbHelper;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost","https://lunchapp.wduan.dev","http://localhost:63342"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/api/v1/sync")

public class SyncController {
    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE})
    public byte[] refresh() {
        LogController.log("GET /api/v1/refresh");
        LogController.log("Syncing"+dbHelper.getD0().countDocuments()+" Daily Orders...");
        dbHelper.getD0().find().forEach(this::reorg);
        LogController.log("Sync complete. Dropping d0 now.");
        //dbHelper.getD0().deleteMany(new Document());
        LogController.log("d0 drop complete.");

        try {
            return new ClassPathResource("emotiguy/3d_arab.jpg").getContentAsByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reorg(Document dOrder) {
        Document temp = dbHelper.getStdl().find(eq("email", dOrder.get("email"))).first();
        if (temp == null) {
            LogController.log("No existing user found, creating new user " + dOrder.get("email"));
            Document tOrder= (Document) dOrder.get("order");
            Document t1 = new Document();
            t1.append("timestamp", tOrder.get("timestamp"));
            t1.append("breadType", tOrder.get("breadType"));
            t1.append("toasted", tOrder.get("toasted"));
            t1.append("subSize", tOrder.get("subSize"));
            t1.append("protein", tOrder.get("protein"));
            t1.append("topping", tOrder.get("topping"));
            t1.append("sauce", tOrder.get("sauce"));

            Document t2 = new Document("email", dOrder.get("email").toString());
            t2.append("fullName", dOrder.get("fullName"));
            t2.append("studentID", dOrder.get("studentID"));
            t2.append("lunchPeriod", dOrder.get("lunchPeriod"));
            t2.append("calories", dOrder.get("calories"));
            t2.append("recentOrder", t1);
            t2.append("orderHistory", new ArrayList<>());

            dbHelper.getStdl().insertOne(t2);
            LogController.log("Inserting " + t2 + " -> stdl");
        } else {
            LogController.log("User found, updating order history " + dOrder.get("email"));
            Bson updates = Updates.combine(
                    Updates.push("orderHistory", temp.get("recentOrder")),
                    Updates.set("recentOrder", dOrder.get("order")),
                    Updates.inc("calories", (Number)dOrder.get("calories"))
            );

           dbHelper.getStdl().updateOne(eq("email", dOrder.get("email")), updates);
        }
    }
    /*async function reorg2(dOrder) {
    let tempString;
    console.log(JSON.stringify(dOrder));
    var order = dOrder.order;
    var timestamp=order.timestamp;
    console.log(timestamp);
    var fullName=dOrder.fullName;
    var email=dOrder.email;
    var studentID=dOrder.studentID;
    var phoneNumber=dOrder.phoneNumber;
    var lunchPeriod=dOrder.lunchPeriod;

    var breadType=order.breadType;
    var protein=order.protein;
    console.log(protein);
    var toasted=order.toasted;
    var topping=order.topping;
    var sauce=order.sauce;

    var fData = await sCollection.findOne({fullName:fullName});
    console.log(fData);
    if(fData===null) {
        console.log("No existing user found, creating new user");
        tempString = "{\n" +
            "  \"email\": \"" + email + "\",\n" +
            "  \"fullName\": \"" + fullName + "\",\n" +
            "  \"lunchPeriod\": " + lunchPeriod + ",\n" +
            "  \"phoneNumber\": \"" + phoneNumber + "\",\n" +
            "  \"studentID\": " + studentID + ",\n" +
            "  \"recentOrder\": {\n" +
            "      \"timestamp\":" + timestamp + ",\n" +
            "      \"breadType\": \"" + breadType + "\",\n" +
            "      \"protein\": " + JSON.stringify(protein) + ",\n" +
            "      \"toasted\": " + toasted + ",\n" +
            "      \"topping\": " + JSON.stringify(topping) + ",\n" +
            "      \"sauce\": " + JSON.stringify(sauce) + " \n" +
            "  },\n" +
            "  \"orderHistory\": []\n" +
            "}";
        //console.log(tempString);
        await sCollection.insertOne(JSON.parse(tempString));
    } else {
        console.log("User found, updating order history");
        tempString = "{\n" +
            "      \"timestamp\":"+timestamp+",\n" +
            "      \"breadType\": \""+breadType+"\",\n" +
            "      \"protein\": "+JSON.stringify(protein)+",\n" +
            "      \"toasted\": "+toasted+",\n" +
            "      \"topping\": "+JSON.stringify(protein)+",\n" +
            "      \"sauce\": "+JSON.stringify(protein)+" \n" +
            "  }";
        var recentOrder = fData.recentOrder;
        await sCollection.updateOne({fullName:fullName},{$push:{orderHistory:recentOrder}});
        await sCollection.updateOne({fullName:fullName},{$set:{recentOrder:JSON.parse(tempString)}});
    }
*/
}
