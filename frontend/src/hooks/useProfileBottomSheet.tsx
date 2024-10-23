import { useState } from "react";
import ProfileBottomSheet from "@_components/ProfileBottomSheet/ProfileBottomSheet";

export interface Profile {
  name: string;
  nickname: string;
  description: string;
  url: string;
}

export default function useProfileBottomSheet(profile: Profile) {
  const [isBottomSheetOpen, setIsBottomSheetOpen] = useState(false);

  const open = () => setIsBottomSheetOpen(true);
  const close = () => setIsBottomSheetOpen(false);

  const profileBottomSheet = (
    <ProfileBottomSheet
      isOpen={isBottomSheetOpen}
      profile={profile}
      close={close}
    />
  );

  return { profileBottomSheet, open, close };
}