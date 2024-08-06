import PleaseCardList from '@_components/PleaseCardList/PleaseCardList';
import { Please } from '@_types/index';

const pleases: Please[] = [
  {
    pleaseId: 1,
    title: '제발 제발 제발',
    description: 'test',
    isInterested: true,
    interestedCount: 1,
  },
  {
    pleaseId: 1,
    title: '제발 제발 제발',
    description: 'test',
    isInterested: true,
    interestedCount: 1,
  },
];

export default function PleaseList() {
  // const { pleaseList, isLoading } = usePleaseList();

  // if (isLoading) {
  //   return <div>로딩중...</div>;
  // }

  return pleases && <PleaseCardList pleases={pleases} />;
}
