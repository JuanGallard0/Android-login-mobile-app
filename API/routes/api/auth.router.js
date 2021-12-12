const router = require("express").Router();

const authControler = require("../../controllers/auth.controller");
const { registerValidator, loginValidator } = require("../../validators/auth.validator");
const runValidator = require("../../middlewares/validator.middleware");

router.post("/signup", registerValidator, runValidator, authControler.register);
router.post("/signin", loginValidator, runValidator, authControler.login);

module.exports = router;
