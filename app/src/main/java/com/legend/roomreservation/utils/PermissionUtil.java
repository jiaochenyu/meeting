package com.legend.roomreservation.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

/**
 * Created by JCY on 2017/12/18.
 * 说明：
 */

public class PermissionUtil {

    static public void openCameraPermission(final Activity context, final MyPermissionListener listener) {
//        boolean isGrant = AndPermission.(context, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        Log.e("permission", "openCameraPermission: " + isGrant);
//        if (!isGrant) {
        AndPermission.with(context)
                .permission(
                        Permission.Group.STORAGE,
                        Permission.Group.CAMERA
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        listener.onSucceed();
                    }
                })
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("提示");
                        alertDialog.setMessage("是否继续授予摄像机权限？");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.execute();
                            }
                        });
                        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.cancel();
                            }
                        });
                        alertDialog.show();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            final SettingService settingService = AndPermission.permissionSetting(context);
                            // 这里使用一个Dialog展示没有这些权限应用程序无法继续运行，询问用户是否去设置中授权。
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                            alertDialog.setTitle("提示");
                            alertDialog.setMessage("没有这些权限应用程序无法继续运行，是否去设置中授权？");
                            alertDialog.setCancelable(false);
                            alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    settingService.execute();
                                }
                            });
                            alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    settingService.cancel();
                                }
                            });
                            alertDialog.show();

                        }
                    }
                })
                .start();
    }


    static public void openPhoneState(final Context context, final MyPermissionListener listener) {
        AndPermission.with(context)
                .permission(
                        Manifest.permission.READ_PHONE_STATE
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        listener.onSucceed();
                    }
                })
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("提示");
                        alertDialog.setMessage("是否继续授权？");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.execute();
                            }
                        });
                        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.cancel();
                            }
                        });
                        alertDialog.show();
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                    final SettingService settingService = AndPermission.permissionSetting(context);
                    // 这里使用一个Dialog展示没有这些权限应用程序无法继续运行，询问用户是否去设置中授权。
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("提示");
                    alertDialog.setMessage("没有这些权限应用程序无法继续运行，是否去设置中授权？");
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            settingService.execute();
                        }
                    });
                    alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            settingService.cancel();
                        }
                    });
                    alertDialog.show();

                }
            }
        }).start();

    }

    /**
     * READ_EXTERNAL_STORAGE
     *
     * @param context
     * @param listener
     */
    static public void openStorage(final Context context, final MyPermissionListener listener) {
        AndPermission.with(context)
                .permission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        listener.onSucceed();
                    }
                })
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("提示");
                        alertDialog.setMessage("是否继续授权使用存储权限？");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.execute();
                            }
                        });
                        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.cancel();
                                listener.failed();
                            }
                        });
                        alertDialog.show();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        listener.failed();
                    }
                }).start();
    }

    static public void openWriteSetting(final Context context, final MyPermissionListener listener) {
        AndPermission.with(context)
                .permission(
                        Manifest.permission.WRITE_SETTINGS
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        listener.onSucceed();
                    }
                })
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("提示");
                        alertDialog.setMessage("是否继续授权？");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.execute();
                            }
                        });
                        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.cancel();
                            }
                        });
                        alertDialog.show();
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                    final SettingService settingService = AndPermission.permissionSetting(context);
                    // 这里使用一个Dialog展示没有这些权限应用程序无法继续运行，询问用户是否去设置中授权。
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("提示");
                    alertDialog.setMessage("没有这些权限应用程序无法继续运行，是否去设置中授权？");
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            settingService.execute();
                        }
                    });
                    alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            settingService.cancel();
                        }
                    });
                    alertDialog.show();

                }
            }
        }).start();
    }

    static public void openInstallPackage(final Context context, final MyPermissionListener listener) {

        AndPermission.with(context)
                .permission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        listener.onSucceed();
                    }
                })
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("提示");
                        alertDialog.setMessage("是否继续授予应用安裝权限？");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.execute();

                            }
                        });
                        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.cancel();
                            }
                        });
                        alertDialog.show();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            final SettingService settingService = AndPermission.permissionSetting(context);
                            // 这里使用一个Dialog展示没有这些权限应用程序无法继续运行，询问用户是否去设置中授权。
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                            alertDialog.setTitle("提示");
                            alertDialog.setMessage("没有这些权限应用程序无法继续运行，是否去设置中授权？");
                            alertDialog.setCancelable(false);
                            alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    settingService.execute();
                                }
                            });
                            alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    settingService.cancel();
                                }
                            });
                            alertDialog.show();

                        }
                    }
                }).start();

    }


    static public void openVoice(final Context context, final MyPermissionListener listener) {
        AndPermission.with(context)
                .permission(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        listener.onSucceed();
                    }
                })
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("友好提醒");
                        alertDialog.setMessage("请开启麦克风权限！");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.execute();
                            }
                        });
                        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executor.cancel();
                            }
                        });
                        alertDialog.show();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            final SettingService settingService = AndPermission.permissionSetting(context);
                            // 这里使用一个Dialog展示没有这些权限应用程序无法继续运行，询问用户是否去设置中授权。
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                            alertDialog.setTitle("提示");
                            alertDialog.setMessage("没有这些权限应用程序无法继续运行，是否去设置中授权？");
                            alertDialog.setCancelable(false);
                            alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    settingService.execute();
                                }
                            });
                            alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    settingService.cancel();
                                }
                            });
                            alertDialog.show();

                        }
                    }
                }).start();


    }


    public abstract static class MyPermissionListener {
        //        public void onSucceed(int requestCode, List<String> grantPermissions) {
//        }
        public void onSucceed() {

        }

        public void failed() {

        }


    }
}
