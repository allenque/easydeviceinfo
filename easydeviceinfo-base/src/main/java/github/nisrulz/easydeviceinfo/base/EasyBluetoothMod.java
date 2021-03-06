/*
 * Copyright (C) 2016 Nishant Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github.nisrulz.easydeviceinfo.base;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Build;

/**
 * The type Easy bluetooth mod.
 */
public class EasyBluetoothMod {
  private final Context context;

  /**
   * Instantiates a new Easy bluetooth mod.
   *
   * @param context
   *     the context
   */
  public EasyBluetoothMod(final Context context) {
    this.context = context;
  }

  /**
   * Gets bluetooth mac.
   *
   * @return the bluetooth mac
   */
  @SuppressWarnings("MissingPermission")
  public final String getBluetoothMAC() {
    String result = "02:00:00:00:00:00";
    if (PermissionUtil.hasPermission(context, Manifest.permission.BLUETOOTH)) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // Hardware ID are restricted in Android 6+
        // https://developer.android.com/about/versions/marshmallow/android-6.0-changes.html#behavior-hardware-id
        // Getting bluetooth mac via reflection for devices with Android 6+
        result = android.provider.Settings.Secure.getString(context.getContentResolver(),
            "bluetooth_address");
      }
      else {
        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
        result = bta != null ? bta.getAddress() : result;
      }
    }
    return CheckValidityUtil.checkValidData(result);
  }
}
