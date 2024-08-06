import { Please } from '@_types/index';
import * as S from './PleaseCard.style';
import { useTheme } from '@emotion/react';
import InterestingIcon from '@_components/Icons/InterestingIcon';

interface PleaseCardProps {
  please: Please;
}

export default function PleaseCard(props: PleaseCardProps) {
  const { please, ...args } = props;

  const theme = useTheme();

  return (
    <div css={S.cardBox} {...args}>
      <div css={S.contentWrapper}>
        <div css={S.headerWrapper}>
          <div css={S.title({ theme })}>{please.title}</div>
          <div css={S.count({ theme })}>
            <span css={S.countAccent({ theme })}>{please.interestCount}명</span>
            이 관심 보이는 중👀
          </div>
        </div>
        <div css={S.description({ theme })}>{please.description}</div>
      </div>
      <div css={S.actionWrapper}>
        <div css={S.actionButtonWrapper}>
          <button css={S.actionButton({ theme })}>
            <InterestingIcon isActive={please.isInterested} />
          </button>
          <div css={S.actionText({ theme })}>관심있어요</div>
        </div>
      </div>
    </div>
  );
}
