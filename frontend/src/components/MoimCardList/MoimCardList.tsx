import * as S from '@_components/MoimCardList/MoimCardList.style';

import MoimCard from '@_components/Card/MoimCard';
import { MoimInfo } from '@_types/index';

interface MoimCardListProps {
  moimInfos: MoimInfo[];
}

export default function MoimCardList(props: MoimCardListProps) {
  const { moimInfos } = props;
  return (
    <div css={S.cardListSection}>
      {moimInfos.map((moimInfo, index) => {
        return (
          <MoimCard key={`${moimInfo.title}${index}`} moimInfo={moimInfo} />
        );
      })}
    </div>
  );
}
