import 'package:flutter/material.dart';
import 'package:flutter_stripe/card_layout.dart';
import 'package:flutter_stripe/flutter_stripe.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Stripe',
      theme: ThemeData(
          fontFamily: 'Exo2',
          brightness: Brightness.light,
          primaryColorBrightness: Brightness.dark,
          accentColor: Colors.blue,
          primaryColor: Colors.blueGrey),
      home: MyHomePage(
        title: "Stripe",
      ),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  bool _cardValid;
  CardController _cardController = CardController();

  bool _requestToken = false;
  String _token;

  Future<void> _validate() async {
    setState(() {
      _requestToken = true;
    });
    StripeCard card = StripeCard(
        cardNumber: _cardController.cardNumber,
        expiryMonth: _cardController.expiryMonth,
        expiryYear: _cardController.expiryYear,
        cvc: _cardController.cvv);
    bool valid = await Stripe.validateCard(card);
    setState(() {
      _cardValid = valid;
    });

    if (valid) {
      _createToken(card);
    } else {
      setState(() {
        _requestToken = false;
      });
    }
  }

  _createToken(StripeCard card) {
    Stripe().createToken(card, (token) {
      print("TOKEN SUCCESS: $token");
      setState(() {
        _requestToken = false;
        _token = token["id"];
      });
    }, (error) {
      print("TOKEN ERROR: $error");
      setState(() {
        _requestToken = false;
        _token = "$error";
      });
    });
  }

  @override
  void initState() {
    Stripe.init("pk_test_GrhIK2WeZUY20twbYC15xNXD");
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Stripe"),
      ),
      body: Container(
        child: Column(
          children: <Widget>[
            CardWidget(
              cardController: _cardController,
            ),
            Center(
              child: !(_cardValid ?? true)
                  ? Text("Card is not valid!")
                  : _requestToken
                      ? CircularProgressIndicator()
                      : Text(_token != null ? _token : "Create token!"),
            )
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _validate,
        tooltip: 'validate',
        child: Icon(Icons.credit_card),
      ),
    );
  }
}
