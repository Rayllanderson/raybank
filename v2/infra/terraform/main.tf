module "alb" {
  source = "./modules/alb"
}

module "ecs" {
  source          = "./modules/ecs"
  raybank_tg_arn  = module.alb.raybank_tg_arn
  keycloak_tg_arn = module.alb.keycloak_tg_arn
}