<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.2" tiledversion="1.3.4" name="DefaultObjects" tilewidth="57" tileheight="64" tilecount="7" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <properties>
   <property name="class" value="SimpleEnemy"/>
   <property name="routeLength" value=""/>
  </properties>
  <image width="50" height="55" source="EnemySimple.png"/>
 </tile>
 <tile id="1">
  <properties>
   <property name="class" value="Hero"/>
   <property name="type" value="fire"/>
  </properties>
  <image width="44" height="58" source="HeroFire.png"/>
 </tile>
 <tile id="2">
  <properties>
   <property name="class" value="Hero"/>
   <property name="type" value="ice"/>
  </properties>
  <image width="54" height="58" source="HeroIce.png"/>
 </tile>
 <tile id="3">
  <properties>
   <property name="class" value="Star"/>
  </properties>
  <image width="34" height="33" source="StarSmall.png"/>
 </tile>
 <tile id="4">
  <properties>
   <property name="class" value="Turret"/>
   <property name="shootingAngle" value=""/>
   <property name="type" value="combined"/>
  </properties>
  <image width="57" height="64" source="TurretCombined.png"/>
 </tile>
 <tile id="5">
  <properties>
   <property name="class" value="Turret"/>
   <property name="shootingAngle" value=""/>
   <property name="type" value="fire"/>
  </properties>
  <image width="57" height="64" source="TurretFire.png"/>
 </tile>
 <tile id="6">
  <properties>
   <property name="class" value="Turret"/>
   <property name="shootingAngle" value=""/>
   <property name="type" value="ice"/>
  </properties>
  <image width="57" height="64" source="TurretIce.png"/>
 </tile>
</tileset>
