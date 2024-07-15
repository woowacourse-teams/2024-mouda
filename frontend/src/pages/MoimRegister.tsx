import Button from '../components/Button/Button';
import MoimInput from '../components/Input/MoimInput';
import { Input_Info_List } from '../constants';
import FormLayout from '../layouts/FormLayout/FormLayout';

export default function MoimRegisterPage() {
  return (
    <FormLayout>
      <FormLayout.Header>모임등록하기</FormLayout.Header>

      <FormLayout.MainForm>
        {Object.entries(Input_Info_List).map(([key, info]) => (
          <MoimInput key={key} data={info} />
        ))}
      </FormLayout.MainForm>

      <FormLayout.BottomButtonWrapper>
        <Button shape="bar">등록하기</Button>
      </FormLayout.BottomButtonWrapper>
    </FormLayout>
  );
}
