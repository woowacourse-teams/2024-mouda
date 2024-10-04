import * as S from './PleaseCard.style';

import InterestingIcon from '@_components/Icons/InterestingIcon';
import { Please } from '@_types/index';
import { common } from '@_common/common.style';
import useInterest from '@_hooks/mutaions/useInterest';
import { useTheme } from '@emotion/react';

interface PleaseCardProps {
  please: Please;
}

export default function PleaseCard(props: PleaseCardProps) {
  const { please, ...args } = props;

  const theme = useTheme();

  const { mutate: changeInterest } = useInterest(please);

  const handleInterestButtonClick = () => {
    changeInterest();
  };

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
        <button css={S.actionButton} onClick={handleInterestButtonClick}>
          <div css={S.actionIconWrapper({ theme })}>
            <InterestingIcon isActive={please.isInterested} />
          </div>
          <div css={[S.actionText({ theme }), common.nonDrag]}>관심있어요</div>
        </button>
      </div>
    </div>
  );
}
