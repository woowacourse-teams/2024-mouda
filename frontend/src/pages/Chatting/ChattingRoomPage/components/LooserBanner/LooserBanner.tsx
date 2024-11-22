import { BetChatRoomDetail } from '@_types/index';
import { useTheme } from '@emotion/react';
import * as S from './LooserBanner.style';

interface LooserBannerProps {
  chatRoomDetail: BetChatRoomDetail;
}

export default function LooserBanner(props: LooserBannerProps) {
  const { chatRoomDetail } = props;

  const theme = useTheme();

  return (
    <div css={S.bannerContainer({ theme })}>
      <div css={S.bannerTextContainer}>
        <span css={S.bannerText({ theme })}>
          축하드립니다 {chatRoomDetail.attributes.loser.nickname}님!&nbsp;
        </span>
        <span css={S.bannerText({ theme })}>
          축하드립니다 {chatRoomDetail.attributes.loser.nickname}님!&nbsp;
        </span>
        <span css={S.bannerText({ theme })}>
          축하드립니다 {chatRoomDetail.attributes.loser.nickname}님!&nbsp;
        </span>
        <span css={S.bannerText({ theme })}>
          축하드립니다 {chatRoomDetail.attributes.loser.nickname}님!&nbsp;
        </span>
      </div>
    </div>
  );
}
