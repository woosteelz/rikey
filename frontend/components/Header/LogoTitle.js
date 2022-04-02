import React from 'react';
import { Image, View } from 'react-native';
import RIKEY_LOGO from '../../assets/images/RIKEY_LOGO.png'

const LogoTitle = () => {

  return (

    <View style={{ flexDirection: 'row', alignItems: 'center', justifyContent: 'center', marginLeft: "12%", marginTop: "7%", marginBottom: "3%" }}>
      <Image 
        source={RIKEY_LOGO}
        style = {{ width:165, height:80 }}
      />
    </View>
    
  )
}

export default LogoTitle;