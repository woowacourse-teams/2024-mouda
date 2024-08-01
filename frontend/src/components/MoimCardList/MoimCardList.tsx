import * as S from './MoimCardList.style';

import { MoimInfo } from '@_types/index';
import { useNavigate } from 'react-router-dom';
import MoimCard from '../MoimCard/MoimCard';

interface MoimCardListProps {
  moimInfos: MoimInfo[];
}

export default function MoimCardList(props: MoimCardListProps) {
  const { moimInfos } = props;

  const navigate = useNavigate();

  const handleMoimCard = (moimId: number) => {
    navigate(`/moim/${moimId}`);
  };

  return (
    <div css={S.cardListSection}>
      {moimInfos.map((moimInfo) => {
        return (
          <MoimCard
            key={moimInfo.moimId}
            moimInfo={moimInfo}
            onClick={() => handleMoimCard(moimInfo.moimId)}
          />
        );
      })}
    </div>
  );
}
