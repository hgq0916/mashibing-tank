package com.mashibing.io;

public enum MsgType {
  TANK_JOIN(TankJoinMsg.class),
  TANK_MOVING(TankMoveMsg.class),
  TANK_STOP(TankStopMsg.class),
  TANK_CHANGE_DIR(TankDirectionChangeMsg.class),
  TANK_DEAD(TankDeadMsg.class),
  BULLET_DEAD(BulletDeadMsg.class),
  BULLET_NEW(BulletNewMsg.class);

  private Class<? extends Msg> msgClazz;

  private MsgType(Class<? extends Msg> msgClazz){
    this.msgClazz = msgClazz;
  }

  public Class<? extends Msg> getMsgClazz() {
    return msgClazz;
  }
}
