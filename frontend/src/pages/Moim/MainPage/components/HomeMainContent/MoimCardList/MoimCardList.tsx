import GET_ROUTES from '@_common/getRoutes';
import MoimCard from './MoimCard/MoimCard';
import { MoimInfo } from '@_types/index';
import { useNavigate } from 'react-router-dom';
import ListContent from '@_layouts/components/ListContent/ListContent';

interface MoimCardListProps {
  moimInfos: MoimInfo[];
}

export default function MoimCardList(props: MoimCardListProps) {
  const { moimInfos } = props;

  const navigate = useNavigate();

  const handleMoimCard = (moimId: number) => {
    navigate(GET_ROUTES.nowDarakbang.moimDetail(moimId));
  };

  return (
    <ListContent>
      {moimInfos.map((moimInfo) => {
        return (
          <MoimCard
            key={moimInfo.moimId}
            moimInfo={moimInfo}
            onClick={() => handleMoimCard(moimInfo.moimId)}
          />
        );
      })}
    </ListContent>
  );
}
