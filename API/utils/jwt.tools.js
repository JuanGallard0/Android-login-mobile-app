const jwt = require("jsonwebtoken");

const secret = "SBUuiEUBOUNE896y788KIjfidmfosds";
const expTime = "14d";

const tools = {};

tools.createToken = (_id) => {
  const payLoad = {
    _id
  };

  return jwt.sign(payLoad, secret, {
    expiresIn: expTime
  });
}

tools.verifyToken = (token) => {
  try {
    return jwt.verify(token, secret);
  }
  catch {
    return false;
  }
}

module.exports = tools;
