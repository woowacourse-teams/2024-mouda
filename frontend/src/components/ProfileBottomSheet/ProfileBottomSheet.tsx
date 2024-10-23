import BottomSheet from '@_components/BottomSheet/BottomSheet';
import { Profile } from '@_hooks/useProfileBottomSheet';
import ProfileInfoItem from './ProfileInfoItem';
import { css, useTheme } from '@emotion/react';
import CloseButton from '@_components/CloseButton/CloseButton';
import ProfileFrame from '@_components/ProfileFrame/ProfileFrame';

interface ProfileBottomSheetProps {
  isOpen: boolean;
  profile: Profile;
  close: () => void;
}

export default function ProfileBottomSheet(props: ProfileBottomSheetProps) {
  const { isOpen, profile, close } = props;

  const theme = useTheme();

  console.log(profile);
  return (
    <BottomSheet
      isOpen={isOpen}
      header={
        <BottomSheet.Header>
          <CloseButton onClick={close} />
        </BottomSheet.Header>
      }
      // TODO: 바텀시트 사이즈에 대한 논의 필요
      size="small"
      onDimmerClick={close}
    >
      <BottomSheet.Content>
        <div
          css={css`
            display: flex;
            flex-direction: column;
            gap: 4.1rem;
          `}
        >
          <div
            css={css`
              display: flex;
              justify-content: center;
            `}
          >
            <div
              css={css`
                display: flex;
                align-items: center;
                justify-content: center;

                width: 123px;
                height: 123px;

                border-radius: 100%;
                box-shadow: 0 8px 24px 0 rgb(149 157 165 / 20%);
              `}
            >
              <ProfileFrame
                width={9}
                height={9}
                borderWidth={0}
                src={profile.url}
              />
            </div>
          </div>
          <div
            css={css`
              display: flex;
              flex-direction: column;
              gap: 1.1rem;
            `}
          >
            <div
              css={css`
                ${theme.typography.label}
                color: ${theme.colorPalette.grey[500]};
              `}
            >
              프로필
            </div>
            <div
              css={css`
                display: flex;
                flex-direction: column;
                gap: 3.7rem;
              `}
            >
              <ProfileInfoItem label="이름" value={profile.name} />
              <ProfileInfoItem label="닉네임" value={profile.nickname} />
              <ProfileInfoItem
                label="소개"
                value={
                  profile.description ||
                  `${profile.name}(${profile.nickname})님의 소개가 없습니다.`
                }
              />
            </div>
          </div>
        </div>
      </BottomSheet.Content>
    </BottomSheet>
  );
}
