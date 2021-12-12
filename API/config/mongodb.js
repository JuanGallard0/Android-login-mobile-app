const debug = require("debug")("app:mongoose");
const mongoose = require("mongoose");

const dburi = "mongodb+srv://juan:uca99@cluster0.owwru.mongodb.net/NDVdb?retryWrites=true&w=majority"

const connect = async() => {
  try {
    await mongoose.connect(dburi, {
      useNewUrlParser: true,
      useUnifiedTopology: true,
      useCreateIndex: true,
      useFindAndModify: false
    });

    debug("Conexion exitosa con la base de datos");
  }
  catch {
    debug("No se pudo conectar con la base de datos");
    process.exit(1);
  }
}

module.exports = {
  connect
}
